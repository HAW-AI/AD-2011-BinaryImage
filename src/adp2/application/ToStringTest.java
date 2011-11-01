package adp2.application;

public class ToStringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(EsserParser.parse("test/fixtures/1x1.esser"));
		System.out.println(EsserParser.parse("test/fixtures/2x2.esser"));
		System.out.println(EsserParser.parse("test/fixtures/3x3.esser"));
		System.out.println(EsserParser.parse("test/fixtures/4x4.esser"));
		System.out.println(EsserParser.parse("test/fixtures/3x1.esser"));
		System.out.println(EsserParser.parse("test/fixtures/1x3.esser"));
		
		System.out.println(EsserParser.parse("test/fixtures/invalid1.esser"));
	}
}
