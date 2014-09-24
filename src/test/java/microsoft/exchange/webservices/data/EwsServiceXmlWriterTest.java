package microsoft.exchange.webservices.data;

import java.io.ByteArrayOutputStream;
import javax.xml.stream.XMLStreamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EwsServiceXmlWriterTest {
    private EwsServiceXmlWriter ewsServiceXmlWriter;

    @Before
    public void setUp() throws XMLStreamException {
        ewsServiceXmlWriter = new EwsServiceXmlWriter(new ExchangeServiceBase() {
            @Override
            protected void processHttpErrorResponse(HttpWebRequest httpWebResponse, Exception webException) throws Exception {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }, new ByteArrayOutputStream());
    }

    @After
    public void tearDown() {
        if (ewsServiceXmlWriter != null) {
            ewsServiceXmlWriter.dispose();
        }
    }

    /**
     * Test of tryConvertObjectToString method, of class EwsServiceXmlWriter.
     */
    @Test
    public void testTryConvertObjectToString() throws XMLStreamException {
        Object longValue = (long) 42;
        OutParam<String> output = new OutParam<String>();
        boolean isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(longValue, output);
        assertTrue("Couldn't convert the long value", isConvertible);
    }

}
