import java.util.Random;

public class id {

		  private static final Random rand = new Random();

		  public static String generateID() {
			int num;
		    num = rand.nextInt(1000);
		    return "CO" + String.format("%03d", num);
		  }
}
