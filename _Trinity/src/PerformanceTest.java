import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PerformanceTest
{
   static final String[] uuids = new String[100000];

   private ArrayList<Object> values;
   private long start;

   static final Pattern BACKSLASHES_PATTERN = Pattern.compile("\\\\");
   static final Pattern NULL_PATTERN = Pattern.compile("\0");
   static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");
   static final Pattern CARRIAGE_RETURN_PATTERN = Pattern.compile("\r");
   static final Pattern TAB_PATTERN = Pattern.compile("\t");
   static final Pattern FORM_FEED_PATTERNS = Pattern.compile("\f");
   static final Pattern BACKSLASH_PATTERN = Pattern.compile("\b");

   static final Pattern NULL_CHAR_PATTERN = Pattern.compile("\0");

   @BeforeClass
   public static void generateUUIDs()
   {
      for (int i = 0; i < uuids.length; i++)
         uuids[i] =
            UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() +
               UUID.randomUUID().toString() + (i % 999 == 0 ? '\0' : '#') + UUID.randomUUID().toString();
   }

   @Before
   public void startTime()
   {
      values = new ArrayList<>();
      start = System.currentTimeMillis();
   }

   @Test
   @Ignore
   public void testScanNodeMethod()
   {
      for (String uuid : uuids)
         values.add(escapeSpecialCharacters(uuid));
      System.out.println("Scan Node Method: " + (System.currentTimeMillis() - start) + "ms");
      assertTrue(uuids.length == values.size());
   }

   @Test
   @Ignore
   public void testNotPreCompiledMethod()
   {
      for (String uuid : uuids)
         values.add(notPreCompiled(uuid));
      System.out.println("Not Pre-Compiled: " + (System.currentTimeMillis() - start) + "ms");
      assertTrue(uuids.length == values.size());
   }

   @Test
   @Ignore
   public void testCharArrayMethod()
   {
      for (String uuid : uuids)
         values.add(addEscapeSequencesForPGCopyOutputStream(uuid));
      System.out.println("Switch Method: " + (System.currentTimeMillis() - start) + "ms");
      assertTrue(uuids.length == values.size());
   }

   @Test
   public void testVarChar()
   {
      for (String uuid : uuids)
         values.add(existingScrubVarChar(uuid));
      System.out.println("Scrub VarChar: " + (System.currentTimeMillis() - start) + "ms");
      assertTrue(uuids.length == values.size());
   }

   @Test
   public void testBetterExpectedPerformance()
   {
      for (String uuid : uuids)
         values.add(removeNullBytesForPostgreSQL(uuid));
      System.out.println("Remove NULL by Switch: " + (System.currentTimeMillis() - start) + "ms");
      assertTrue(uuids.length == values.size());
   }

   private static String existingScrubVarChar(String input)
   {
      if (input == null || !input.contains("\0"))
         return input;
      return NULL_CHAR_PATTERN.matcher(input).replaceAll("");
   }

   private static String removeNullBytesForPostgreSQL(String input)
   {
      if (input == null || !input.contains("\0"))
         return input;
      StringBuilder ret = new StringBuilder();
      for (char ch : input.toCharArray())
         if (ch != '\0')
            ret.append(ch);
      return ret.toString();
   }

   /**
    * Add escape sequences to any characters which will interfere with publishing to a {@code PGCopyOutputStream}.
    * <p>
    * The input to a {@code PGCopyOutputStream} assumed that tabs and newline character actually delimit fields and
    * records, respectively, so these characters need to be escaped before pushing to the OutputStream. If the String
    * passed in is Null, then return the special Null character that PostgreSQL expects for a Null record.
    *
    * @param input String value to add escape sequences to any characters which will interfere with publishing to a
    *        {@code PGCopyOutputStream}.
    * @return A String read for insertion into a {@code PGCopyOutputStream}.
    */
   private static String addEscapeSequencesForPGCopyOutputStream(String input)
   {
      if (input == null)
         return "\\N";

      StringBuilder ret = new StringBuilder();
      for (char ch : input.toCharArray())
         switch (ch)
         {
            case '\n':
               ret.append("\\n");
               break;
            case '\t':
               ret.append("\\t");
               break;
            case '\r':
               ret.append("\\r");
               break;
            case '\0':
               break;
            case '\f':
               ret.append("\\f");
               break;
            case '\b':
               ret.append("\\b");
               break;
            default:
               ret.append(ch);
         }
      return ret.toString();
   }

   private static String escapeSpecialCharacters(String str)
   {
      if (str == null)
         return "\\N"; //PostgreSQL Null value

      str = BACKSLASHES_PATTERN.matcher(str).replaceAll("/");
      str = NEWLINE_PATTERN.matcher(str).replaceAll("\\\\n");
      str = CARRIAGE_RETURN_PATTERN.matcher(str).replaceAll("\\\\r");
      str = TAB_PATTERN.matcher(str).replaceAll("\\\\t");
      str = NULL_PATTERN.matcher(str).replaceAll("");
      str = FORM_FEED_PATTERNS.matcher(str).replaceAll("\\\\f");
      return BACKSLASH_PATTERN.matcher(str).replaceAll("\\\\b");
   }

   private static String notPreCompiled(String str)
   {
      if (str == null)
         return "\\N";

      str = str.replaceAll("\\\\", "/");
      str = str.replaceAll("\t", "\\\\t");
      str = str.replaceAll("\n", "\\\\n");
      str = str.replaceAll("\r", "\\\\r");
      str = str.replaceAll("\0", "");
      str = str.replaceAll("\f", "\\\\f");
      return str.replaceAll("\b", "\\\\b");
   }
}
