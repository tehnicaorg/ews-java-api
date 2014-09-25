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
     * @throws javax.xml.stream.XMLStreamException
     */
    @Test
    public void testTryConvertObjectToString() throws XMLStreamException {
        Object longValue = (long) 42;
        OutParam<String> output = new OutParam<String>();

        boolean isConvertible;
        ConvertibleEnum convertibleEnum = ConvertibleEnum.ORANGE;
        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(convertibleEnum, output);
        assertTrue("Couldn't convert an enum", isConvertible);
        assertEquals("Wrong value for the converted enum", "ORANGE", output.getParam());

        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(Boolean.FALSE, output);
        assertTrue("Couldn't convert Boolean.FALSE", isConvertible);
        assertEquals("Wrong value for the converted Boolean.FALSE", "false", output.getParam());

        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(Boolean.TRUE, output);
        assertTrue("Couldn't convert Boolean.TRUE", isConvertible);
        assertEquals("Wrong value for the converted Boolean.TRUE", "true", output.getParam());

        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(null, output);
        assertTrue("Couldn't convert null", isConvertible);
        assertEquals("Wrong value for the converted null", null, output.getParam());

        String emptyString = "";
        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(emptyString, output);
        assertTrue("Couldn't convert an empty string", isConvertible);
        assertEquals("Wrong value for the converted empty string", emptyString, output.getParam());

        String string = " test 123 ăș < > \" ' &";
        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(string, output);
        assertTrue("Couldn't convert a string", isConvertible);
        assertEquals("Wrong value for the converted string", string, output.getParam());

        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(-42, output);
        assertTrue("Couldn't convert an Integer", isConvertible);
        assertEquals("Wrong value for the converted Integer", "-42", output.getParam());

        final String text = "i search string provider";
        ISearchStringProvider stringProvider = new ISearchStringProvider() {
            public String getSearchString() {
                return text;
            }
        };
        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(stringProvider, output);
        assertTrue("Couldn't convert an object implementing ISearchStringProvider", isConvertible);
        assertEquals("Wrong value for the converted ISearchStringProvider", text, output.getParam());

        isConvertible = ewsServiceXmlWriter.tryConvertObjectToString(longValue, output);
        assertTrue("Couldn't convert the long value", isConvertible);
    }

    private enum ConvertibleEnum {

        APPLE, ORANGE, BANANA, PEAR

    };

}
