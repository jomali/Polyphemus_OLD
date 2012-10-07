
public class Tests {
	
	public static void main(String[] args) {
		int capacidad = 0;
		int contador = 0;
		int cantidad1 = 5;
		int cantidad2 = 10;
		int c;
		
		c = (contador + cantidad1) % capacidad;
		System.out.println(contador+ " + "+ cantidad1+ " = "+ c);

		c = (contador - cantidad1) % capacidad;
		System.out.println(contador+ " - "+ cantidad1+ " = "+ c);

		c = (contador + cantidad2) % capacidad;
		System.out.println(contador+ " + "+ cantidad2+ " = "+ c);

		c = (contador - cantidad2) % capacidad;
		System.out.println(contador+ " - "+ cantidad2+ " = "+ c);
	}

}
