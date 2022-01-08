package com.etnetera.hr;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JavaScriptFrameworkRepository JFRepository;

    public static boolean isSetup = false;

    @Test
    public void error4xxTest() throws Exception {
        this.mvc.perform(get("/err400")).andExpect(status().is4xxClientError());
    }

    @Test
    public void createJSFrameWorkTest() throws Exception {
        JavaScriptFramework jsf = new JavaScriptFramework(6L,"MeteorJS",new String[]{"5.3.1"}, new Date(), 2222);
        mvc.perform( MockMvcRequestBuilders
                .post("/frameworks")
                .content(asJsonString(jsf))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateJSFrameWorkTest() throws Exception {
        JavaScriptFramework jsf = new JavaScriptFramework(1L,"Angular",new String[]{"5.3.2"}, new Date(), 3333);
        mvc.perform( MockMvcRequestBuilders
                .put("/frameworks/" + jsf.getId())
                .content(asJsonString(jsf))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getJSFrameWorkByIdTest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/frameworks/" + 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void getAllJSFrameWorksTest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/frameworks")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteJSFrameWorkTest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .delete("/frameworks/" + 2))
                .andExpect(status().isOk());
    }

    @Test
    //Exact match
    public void searchJSFByName() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/frameworks/search/angular")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Angular"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Angular"));
    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // fill with dummy data before tests
    @Before
    public void setup(){
        if(!isSetup) {
            List<JavaScriptFramework> jsfs = new ArrayList<>();
            jsfs.add(new JavaScriptFramework(1L, "Angular", new String[]{"2.3.1", "2.4.1"}, new Date(), 1000));
            jsfs.add(new JavaScriptFramework(2L, "Angular", new String[]{"7.3.1"}, new Date(), 2000));
            jsfs.add(new JavaScriptFramework(3L, "React", new String[]{"3.3.3"}, new Date(), 3000));
            jsfs.add(new JavaScriptFramework(4L, "VueJS", new String[]{"0.7.1"}, new Date(), 4000));
            jsfs.add(new JavaScriptFramework(5L, "AngularJS", new String[]{"1.1.1"}, new Date(), 5000));
            JFRepository.saveAll(jsfs);

            isSetup = true;
        }
    }


}
