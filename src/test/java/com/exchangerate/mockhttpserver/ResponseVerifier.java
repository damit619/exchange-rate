package com.exchangerate.mockhttpserver;

import com.exchangerate.exception.OpenExchangeException;

import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class ResponseVerifier {

    private final Supplier<?> supplier;
    private final MockServer mockServer;

    public ResponseVerifier(MockServer mockServer, Supplier<?> supplier) {
        assertThat("Supplier is null", supplier, is(notNullValue()));
        this.supplier = supplier;
        this.mockServer = mockServer;
    }

    public MockServer expectClientError() {
        try {
            supplier.get();
        } catch (Exception ex) {
            assertThat(ex, instanceOf(OpenExchangeException.class));
            assertThat(((OpenExchangeException) ex).isClientError(), is(true));
        }
        return mockServer;
    }

    public MockServer expectServerError() {
        try {
            supplier.get();
        } catch (Exception ex) {
            assertThat(ex, instanceOf(OpenExchangeException.class));
            assertThat(((OpenExchangeException) ex).isServerError(), is(true));
        }
        return mockServer;
    }

    public <T> MockServer expectResponse(T response) {
        assertThat(supplier.get(), is(equalTo(response)));
        return mockServer;
    }

    public MockServer expectNoContent() {
        assertThat(supplier.get(), is(nullValue()));
        return mockServer;
    }
}
