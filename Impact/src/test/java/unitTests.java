import numberrangesummarizer.GroupSolve;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

class unitTests {
    //I assume the collect method is called before the summarizeCollection method
    //All string validation occurs in the collect method
    //As such I will be testing this method

    GroupSolve obj;

    @BeforeEach
    public void create(){
        obj = new GroupSolve();
    }

    @AfterEach
    public void restore(){
        obj = null;
    }

    @Test
    public void testEmpty(){
        //Should return empty collection
        Collection<Integer> expected = null;
        Collection<Integer> actual = obj.collect("");
        assertEquals(expected,actual);
    }

    @Test
    public void testCommaCheck(){
        Collection<Integer> expected = null;
        Collection<Integer> actual = obj.collect("1,2,3,4 5");
        assertEquals(expected,actual);
    }

    @Test
    public void testDigitCheck(){

        Collection<Integer> expected = null;
        Collection<Integer> actual = obj.collect("1,2,a,4,5");
        assertEquals(expected,actual);
    }

    @Test
    public void testNull(){

        Collection<Integer> expected = null;
        Collection<Integer> actual = obj.collect(null);
        assertEquals(expected,actual);
    }

    @Test
    public void testIllegalChars(){

        Collection<Integer> expected = null;
        Collection<Integer> actual = obj.collect("!,@,#");
        assertEquals(expected,actual);
    }

    @Test
    public void testSpaces(){

        String expected = "1-3, 5, 44";
        String actual = obj.summarizeCollection(obj.collect("  1,2,3,44,5  "));
        assertEquals(expected,actual);
    }
}