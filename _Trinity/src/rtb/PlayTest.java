package rtb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayTest {

    @Test
    public void testSwap() throws SecurityException, IllegalArgumentException,
            NoSuchFieldException, IllegalAccessException {
        // Start by swapping two Strings.
        TestObject obj = new TestObject();
        obj.a = "left";
        obj.b = "right";
        Play.swap(obj, "a", "b");
        assertEquals("right", obj.a);
        assertEquals("left", obj.b);

        // Swap primitive values, since ultimately this is the first use-case
        // that cannot be done simply in Java.
        PrimitiveObject prim = new PrimitiveObject();
        prim.a = 5;
        prim.b = 12;
        Play.swap(prim, "a", "b");
        assertEquals(12, prim.a);
        assertEquals(5, prim.b);
        
        // Private and final objects are not safe.
        FinalObject fin = new FinalObject("left", "right");
        Play.swap(fin, "a", "b");
        assertEquals("right", fin.a);
        assertEquals("left", fin.b);
        
        // Static values can be swapped.
        StaticObject.a = "left";
        StaticObject.b = "right";
        Play.swap(new StaticObject(), "a", "b");
        assertEquals("right", StaticObject.a);
        assertEquals("left", StaticObject.b);
        
        // Work with non-primitive fields.
        TestObject thingOne = new TestObject();
        thingOne.a = "aOne";
        thingOne.b = "bOne";
        TestObject thingTwo = new TestObject();
        thingTwo.a = "aTwo";
        thingTwo.b = "bTwo";
        ComplexObject comp = new ComplexObject();
        comp.x = thingOne;
        comp.y = thingTwo;
        Play.swap(comp, "x", "y");
        assertEquals(thingTwo, comp.x);
        assertEquals("aTwo", comp.x.a);
        assertEquals("bTwo", comp.x.b);
        assertEquals(thingOne, comp.y);
        assertEquals("aOne", comp.y.a);
        assertEquals("bOne", comp.y.b);
    }

    class TestObject {
        String a;
        String b;
    }
    
    class PrimitiveObject {
        int a;
        int b;
    }
    
    private final class FinalObject {
        private final String a;
        private final String b;
        
        private FinalObject(String a, String b) {
            this.a = a;
            this.b = b;
        }
    }
    
    static class StaticObject {
        static String a;
        static String b;
    }
    
    class ComplexObject {
        TestObject x;
        TestObject y;
    }
}