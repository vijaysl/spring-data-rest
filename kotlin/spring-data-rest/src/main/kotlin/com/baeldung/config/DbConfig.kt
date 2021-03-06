package com.baeldung.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.Properties
import javax.sql.DataSource

//@Configuration
@EnableJpaRepositories(basePackages = arrayOf("com.baeldung.repositories"))
// @PropertySource("persistence-h2.properties")
// @PropertySource("persistence-hsqldb.properties")
// @PropertySource("persistence-derby.properties")
class DbConfig {

    @Autowired
    private val env: Environment? = null

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        if (env != null) {
            dataSource.setDriverClassName(env.getProperty("driverClassName")!!)
        }
        dataSource.setUrl(env?.getProperty("url"))
        dataSource.setUsername(env?.getProperty("user"))
        dataSource.setPassword(env?.getProperty("password"))
        return dataSource
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.setDataSource(dataSource())
        em.setPackagesToScan(*arrayOf("com.baeldung.models"))
        em.setJpaVendorAdapter(HibernateJpaVendorAdapter())
        em.setJpaProperties(additionalProperties())
        return em
    }

    internal fun additionalProperties(): Properties {
        val hibernateProperties = Properties()
        if (env!!.getProperty("hibernate.hbm2ddl.auto") != null) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env!!.getProperty("hibernate.hbm2ddl.auto"))
        }
        if (env!!.getProperty("hibernate.dialect") != null) {
            hibernateProperties.setProperty("hibernate.dialect", env!!.getProperty("hibernate.dialect"))
        }
        if (env!!.getProperty("hibernate.show_sql") != null) {
            hibernateProperties.setProperty("hibernate.show_sql", env!!.getProperty("hibernate.show_sql"))
        }
        return hibernateProperties
    }
}
