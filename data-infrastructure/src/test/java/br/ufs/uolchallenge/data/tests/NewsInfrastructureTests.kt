package br.ufs.uolchallenge.data.tests

import br.ufs.uolchallenge.data.NewsInfrastructure
import br.ufs.uolchallenge.data.rest.WebServiceFactory
import org.junit.Before
import org.junit.Test

/**
 * Created by bira on 11/3/17.
 */

class NewsInfrastructureTests {

    lateinit var infrastructure: NewsInfrastructure

    @Before fun `before each test`() {
        val webservice = WebServiceFactory.create(false)
        infrastructure = NewsInfrastructure()
    }

    @Test fun `should return no values with degenerated implementation`() {
        infrastructure.latestNews()
                .test()
                .assertNoValues()
                .assertComplete()
    }

}