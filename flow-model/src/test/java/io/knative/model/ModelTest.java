/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.knative.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.knativeflow.model.ProcessorFlow;
import io.knativeflow.model.ProcessorNode;
import io.knativeflow.serialization.SerializedFlow;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hhiden
 */
public class ModelTest {
    
    public ModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void modelTest() throws Exception {
        ProcessorFlow flow = new ProcessorFlow();
        flow.setName("Urban Observatory Flow");
        ProcessorNode n1 = new ProcessorNode();
        n1.createOutput();
        n1.setDisplayName("DataFeed");
        n1.setkSVCName("urban-feed");
        
        ProcessorNode n2 = new ProcessorNode();
        n2.createInput();
        n2.createOutput();
        n2.setDisplayName("Filter");
        n2.setkSVCName("feed-filter");
        
        ProcessorNode n3 = new ProcessorNode();
        n3.createInput();
        n3.setDisplayName("MessageDumper");
        n3.setkSVCName("dumper");
        
        flow.addProcessorNode(n1);
        flow.addProcessorNode(n2);
        flow.addProcessorNode(n3);
        
        flow.linkNodes(n1, n2);
        flow.linkNodes(n2, n3);
        
        
        SerializedFlow sFlow = new SerializedFlow(flow);
        ObjectMapper mapper = new ObjectMapper();
        String flowJson = mapper.writeValueAsString(sFlow);
        System.out.println(flowJson);
        
        SerializedFlow recreatedFlow = mapper.readValue(flowJson, SerializedFlow.class);
        ProcessorFlow reconstituted = new ProcessorFlow(recreatedFlow);
        
        String reconstitutedJson = mapper.writeValueAsString(new SerializedFlow(reconstituted));
        System.out.println(reconstitutedJson);
        assertTrue(flowJson.equals(reconstitutedJson));
    }
    
}
