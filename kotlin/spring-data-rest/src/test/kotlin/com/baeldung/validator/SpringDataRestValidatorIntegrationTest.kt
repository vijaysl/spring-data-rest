package com.baeldung.validator

import com.baeldung.SpringDataRestApplication
import com.baeldung.models.WebsiteUser
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = arrayOf(SpringDataRestApplication::class))
@WebAppConfiguration
class SpringDataRestValidatorIntegrationTest {

    private var mockMvc: MockMvc? = null

    @Autowired
    protected var wac: WebApplicationContext? = null

    @Before
    fun setup() {
        mockMvc = webAppContextSetup(wac!!).build()
    }

    @Test
    @Throws(Exception::class)
    fun whenStartingApplication_thenCorrectStatusCode() {
        mockMvc!!.perform(get("/users")).andExpect(status().is2xxSuccessful)
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewCorrectUser_thenCorrectStatusCodeAndResponse() {
        val user = WebsiteUser("John Doe", "john.doe@john.com")
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON).content(ObjectMapper().writeValueAsString(user))).andExpect(status().is2xxSuccessful).andExpect(redirectedUrl("http://localhost/users/1"))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithoutName_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser(null, "john.doe@john.com")
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON).content(ObjectMapper().writeValueAsString(user))).andExpect(status().isPartialContent).andExpect(redirectedUrl(null.toString()))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithEmptyName_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser("", "john.doe@john.com")
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON).content(ObjectMapper().writeValueAsString(user))).andExpect(status().isPartialContent).andExpect(redirectedUrl(null.toString()))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithoutEmail_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser("John Doe")
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON).content(ObjectMapper().writeValueAsString(user))).andExpect(status().isPartialContent).andExpect(redirectedUrl(null.toString()))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithEmptyEmail_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser("John Doe" ,"")
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON).content(ObjectMapper().writeValueAsString(user))).andExpect(status().isPartialContent).andExpect(redirectedUrl(null.toString()))
    }

    companion object {
        val URL = "http://localhost"
    }
}
