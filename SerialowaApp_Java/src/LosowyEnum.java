import java.util.Random;

/**Klasa do losowania Enuma, które s¹ podawane przez klasy zawieraj¹ce enumy
 * @author Szymon
 *
 *
 */
public class LosowyEnum<T extends Enum<T>>
	{
		private static final Random rand = new Random();
		private T[] wartosci;
		
		public LosowyEnum(Class<T> x)
		{
			wartosci = x.getEnumConstants();
		}
		
		public T losuj() {
			return wartosci[rand.nextInt(wartosci.length)];
		}
	}
