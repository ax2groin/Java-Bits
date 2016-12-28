import java.nio.ByteBuffer;
import org.junit.Test;
import static org.junit.Assert.fail;

public class DateAsByteArray
{
   @Test
   public void dateAsByteArray()
   {
      byte[] bytes = ByteBuffer.allocate(8).putLong(System.currentTimeMillis()).array();
      String tmp = "";
      for (byte b : bytes)
         tmp += b + " ";

      System.out.println(tmp);
   }
}
