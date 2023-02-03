package com.fstl.testing.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ContextConfiguration(classes = {CustomerRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.fstl.testing.customer"})
@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldSelectCustomerByPhoneNumber() {
        // Given
        UUID id = UUID.randomUUID();
        String phoneNumber = "0000";
        Customer customer = new Customer(id, "Abel", phoneNumber);

        // When
        underTest.save(customer);

        // Then
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    /**
     * Method under test: {@link CustomerRepository#selectCustomerByPhoneNumber(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSelectCustomerByPhoneNumber() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint ["PUBLIC.UK_ROSD2GUVS3I1AGKPLV5N8VU82_INDEX_5 ON PUBLIC.CUSTOMER(PHONE_NUMBER) VALUES 1"; SQL statement:
        //   insert into customer (name, phone_number, id) values (?, ?, ?) [23505-200]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement
        //       at jdk.proxy4.$Proxy112.selectCustomerByPhoneNumber(null)
        //   org.hibernate.exception.ConstraintViolationException: could not execute statement
        //       at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:59)
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:200)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3235)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3760)
        //       at org.hibernate.action.internal.EntityInsertAction.execute(EntityInsertAction.java:107)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:604)
        //       at org.hibernate.engine.spi.ActionQueue.lambda$executeActions$1(ActionQueue.java:478)
        //       at java.util.LinkedHashMap.forEach(LinkedHashMap.java:721)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:475)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:348)
        //       at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:40)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:102)
        //       at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1352)
        //       at org.hibernate.internal.SessionImpl.flush(SessionImpl.java:1339)
        //       at org.hibernate.query.internal.NativeQueryImpl.beforeQuery(NativeQueryImpl.java:264)
        //       at org.hibernate.query.internal.AbstractProducedQuery.list(AbstractProducedQuery.java:1528)
        //       at org.hibernate.query.internal.AbstractProducedQuery.getSingleResult(AbstractProducedQuery.java:1578)
        //       at jdk.proxy4.$Proxy112.selectCustomerByPhoneNumber(null)
        //   org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Violation d'index unique ou clé primaire: "PUBLIC.UK_ROSD2GUVS3I1AGKPLV5N8VU82_INDEX_5 ON PUBLIC.CUSTOMER(PHONE_NUMBER) VALUES 1"
        //   Unique index or primary key violation: "PUBLIC.UK_ROSD2GUVS3I1AGKPLV5N8VU82_INDEX_5 ON PUBLIC.CUSTOMER(PHONE_NUMBER) VALUES 1"; SQL statement:
        //   insert into customer (name, phone_number, id) values (?, ?, ?) [23505-200]
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:459)
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:429)
        //       at org.h2.message.DbException.get(DbException.java:205)
        //       at org.h2.message.DbException.get(DbException.java:181)
        //       at org.h2.index.BaseIndex.getDuplicateKeyException(BaseIndex.java:103)
        //       at org.h2.mvstore.db.MVSecondaryIndex.checkUnique(MVSecondaryIndex.java:221)
        //       at org.h2.mvstore.db.MVSecondaryIndex.add(MVSecondaryIndex.java:196)
        //       at org.h2.mvstore.db.MVTable.addRow(MVTable.java:531)
        //       at org.h2.command.dml.Insert.insertRows(Insert.java:195)
        //       at org.h2.command.dml.Insert.update(Insert.java:151)
        //       at org.h2.command.CommandContainer.update(CommandContainer.java:198)
        //       at org.h2.command.Command.executeUpdate(Command.java:251)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdateInternal(JdbcPreparedStatement.java:191)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdate(JdbcPreparedStatement.java:152)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:197)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3235)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3760)
        //       at org.hibernate.action.internal.EntityInsertAction.execute(EntityInsertAction.java:107)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:604)
        //       at org.hibernate.engine.spi.ActionQueue.lambda$executeActions$1(ActionQueue.java:478)
        //       at java.util.LinkedHashMap.forEach(LinkedHashMap.java:721)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:475)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:348)
        //       at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:40)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:102)
        //       at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1352)
        //       at org.hibernate.internal.SessionImpl.flush(SessionImpl.java:1339)
        //       at org.hibernate.query.internal.NativeQueryImpl.beforeQuery(NativeQueryImpl.java:264)
        //       at org.hibernate.query.internal.AbstractProducedQuery.list(AbstractProducedQuery.java:1528)
        //       at org.hibernate.query.internal.AbstractProducedQuery.getSingleResult(AbstractProducedQuery.java:1578)
        //       at jdk.proxy4.$Proxy112.selectCustomerByPhoneNumber(null)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        customerRepository.save(customer);
        customerRepository.save(customer1);
        customerRepository.selectCustomerByPhoneNumber("foo");
    }

    @Test
    void itNotShouldSelectCustomerByPhoneNumberWhenNumberDoesNotExists() {
        // Given
        String phoneNumber = "0000";

        // When
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);

        // Then
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    void itShoudSaveCustomer() {
        //Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Armel", "0000");

        //When
        underTest.save(customer);

        //Then
        Optional<Customer> optionalCustomer = underTest.findById(id);
        Assertions.assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
//                    assertThat(c.getId()).isEqualTo(id);
//                    assertThat(c.getName()).isEqualTo("Armel");
//                    assertThat(c.getPhoneNumber()).isEqualTo("0000");
                    assertThat(c).isEqualToComparingFieldByField(customer);
                })
        ;
    }

    @Test
    void itShouldNotSaveCustomerWhenNameIsNull() {
        // Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, null, "0000");

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(customer))
                .hasMessageContaining("not-null property references a null or transient value : com.fstl.testing.customer.Customer.name")
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void itShouldNotSaveCustomerWhenPhoneNumberIsNull() {
        // Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Alex", null);

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(customer))
                .hasMessageContaining("not-null property references a null or transient value : com.fstl.testing.customer.Customer.phoneNumber")
                .isInstanceOf(DataIntegrityViolationException.class);

    }
}