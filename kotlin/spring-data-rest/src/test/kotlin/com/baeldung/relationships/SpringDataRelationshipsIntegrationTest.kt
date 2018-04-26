package com.baeldung.relationships

import com.baeldung.SpringDataRestApplication
import com.baeldung.models.Address
import com.baeldung.models.Author
import com.baeldung.models.Book
import com.baeldung.models.Library
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(SpringDataRestApplication::class), webEnvironment = WebEnvironment.DEFINED_PORT)
class SpringDataRelationshipsIntegrationTest {

    @Autowired
    private val template: TestRestTemplate? = null

    @Test
    @Throws(JSONException::class)
    fun whenSaveOneToOneRelationship_thenCorrect() {
        val library = Library(LIBRARY_NAME)
        template!!.postForEntity(LIBRARY_ENDPOINT, library, Library::class.java)

        val address = Address("Main street, nr 1")
        template.postForEntity(ADDRESS_ENDPOINT, address, Address::class.java)

        val requestHeaders = HttpHeaders()
        requestHeaders.add("Content-type", "text/uri-list")
        val httpEntity = HttpEntity("$ADDRESS_ENDPOINT/1", requestHeaders)
        template.exchange("$LIBRARY_ENDPOINT/1/libraryAddress", HttpMethod.PUT, httpEntity, String::class.java)

        val libraryGetResponse = template.getForEntity("$ADDRESS_ENDPOINT/1/library", Library::class.java)
        assertEquals(
            "library is incorrect",
            libraryGetResponse.body?.name,
            LIBRARY_NAME
        )
    }

    @Test
    @Throws(JSONException::class)
    fun whenSaveOneToManyRelationship_thenCorrect() {
        val library = Library(LIBRARY_NAME)
        template!!.postForEntity(LIBRARY_ENDPOINT, library, Library::class.java)

        val book1 = Book("Dune")
        template.postForEntity(BOOK_ENDPOINT, book1, Book::class.java)

        val book2 = Book("1984")
        template.postForEntity(BOOK_ENDPOINT, book2, Book::class.java)

        val requestHeaders = HttpHeaders()
        requestHeaders.add("Content-type", "text/uri-list")
        val bookHttpEntity = HttpEntity("$LIBRARY_ENDPOINT/1", requestHeaders)
        template.exchange("$BOOK_ENDPOINT/1/library", HttpMethod.PUT, bookHttpEntity, String::class.java)
        template.exchange("$BOOK_ENDPOINT/2/library", HttpMethod.PUT, bookHttpEntity, String::class.java)

        val libraryGetResponse = template.getForEntity("$BOOK_ENDPOINT/1/library", Library::class.java)
        assertEquals(
            "library is incorrect",
            libraryGetResponse.body?.name,
            LIBRARY_NAME
        )
    }

    @Test
    @Throws(JSONException::class)
    fun whenSaveManyToManyRelationship_thenCorrect() {
        val author1 = Author(AUTHOR_NAME)
        template!!.postForEntity(AUTHOR_ENDPOINT, author1, Author::class.java)

        val book1 = Book("Animal Farm")
        template.postForEntity(BOOK_ENDPOINT, book1, Book::class.java)

        val book2 = Book("1984")
        template.postForEntity(BOOK_ENDPOINT, book2, Book::class.java)

        val requestHeaders = HttpHeaders()
        requestHeaders.add("Content-type", "text/uri-list")
        val httpEntity = HttpEntity("$BOOK_ENDPOINT/1\n$BOOK_ENDPOINT/2", requestHeaders)
        template.exchange("$AUTHOR_ENDPOINT/1/books", HttpMethod.PUT, httpEntity, String::class.java)

        val jsonResponse = template.getForObject("$BOOK_ENDPOINT/1/authors", String::class.java)
        val jsonObj = JSONObject(jsonResponse).getJSONObject("_embedded")
        val jsonArray = jsonObj.getJSONArray("authors")
        assertEquals("author is incorrect", jsonArray.getJSONObject(0)
            .getString("name"), AUTHOR_NAME)
    }

    companion object {

        private val BOOK_ENDPOINT = "http://localhost:8080/books/"
        private val AUTHOR_ENDPOINT = "http://localhost:8080/authors/"
        private val ADDRESS_ENDPOINT = "http://localhost:8080/addresses/"
        private val LIBRARY_ENDPOINT = "http://localhost:8080/libraries/"

        private val LIBRARY_NAME = "My Library"
        private val AUTHOR_NAME = "George Orwell"
    }
}
