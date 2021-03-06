/*
Copyright 2018 The Knative Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package main

import (
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"math/rand"
	"net/http"
	"strconv"
	"time"

	"github.com/google/uuid"
	"github.com/knative/pkg/cloudevents"
	"github.com/pkg/errors"
)

var (
	sink      string
	periodStr string

	httpClient = &http.Client{}
)

func init() {
	flag.StringVar(&sink, "sink", "", "the host url to heartbeat to")
	flag.StringVar(&periodStr, "period", "1000", "the number of milliseconds between heartbeats")
}

func main() {
	flag.Parse()
	var period time.Duration
	if p, err := strconv.Atoi(periodStr); err != nil {
		period = time.Duration(400) * time.Millisecond
	} else {
		period = time.Duration(p) * time.Millisecond
	}

	ticker := time.NewTicker(period)
	for {
		s1 := rand.NewSource(time.Now().UnixNano())
		r1 := rand.New(s1)
		f1 := r1.Float64()

		go func() {
			if err := postMessage(f1); err != nil {
				log.Printf("sending event to channel failed: %v", err)
			}
		}()

		<-ticker.C
	}
}

// Creates a CloudEvent Context for a given heartbeat.
func cloudEventsContext() *cloudevents.EventContext {
	return &cloudevents.EventContext{
		CloudEventsVersion: cloudevents.CloudEventsVersion,
		EventType:          "dev.knative.source.heartbeats",
		EventID:            uuid.New().String(),
		Source:             "heartbeats-demo",
		EventTime:          time.Now(),
	}
}

func postMessage(f1 float64) error {
	ctx := cloudEventsContext()

	req, err := cloudevents.Binary.NewRequest(sink, f1, *ctx)
	if err != nil {
		return errors.Wrap(err, "failed to create http request")
	}
	resp, err := httpClient.Do(req)
	if err != nil {
		return errors.Wrap(err, "request failed")
	}

	defer resp.Body.Close()
	if resp.StatusCode >= 300 {
		body, _ := ioutil.ReadAll(resp.Body)
		return fmt.Errorf("request failed: code: %d, body: %s", resp.StatusCode, string(body))
	}

	io.Copy(ioutil.Discard, resp.Body)
	return nil
}
