package adp2.application;

public class ToStringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EsserImage image11 = EsserParser.parse("test/fixtures/1x1.esser");
		System.out.println(String.format("1x1: width:%s, height:%s",image11.width(), image11.height()));
		
		EsserImage image22 = EsserParser.parse("test/fixtures/2x2.esser");
		System.out.println(String.format("2x2: width:%s, height:%s",image22.width(), image22.height()));
		
		EsserImage image33 = EsserParser.parse("test/fixtures/3x3.esser");
		System.out.println(String.format("3x3: width:%s, height:%s",image33.width(), image33.height()));
		
		EsserImage image44 = EsserParser.parse("test/fixtures/4x4.esser");
		System.out.println(String.format("4x4: width:%s, height:%s",image44.width(), image44.height()));
		
		EsserImage image31 = EsserParser.parse("test/fixtures/3x1.esser");
		System.out.println(String.format("3x1: width:%s, height:%s",image31.width(), image31.height()));
		
		EsserImage image13 = EsserParser.parse("test/fixtures/1x3.esser");
		System.out.println(String.format("1x3: width:%s, height:%s",image13.width(), image13.height()));
	}
}
