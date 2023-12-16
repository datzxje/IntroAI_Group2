package input;//package input;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
//public class ReadVerticesFile {
//    public static Vertex[] readVerticesFromFile(String filePath) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            int vertexCount = 0;
//
//            // Initialize the array with the constant size
//            Vertex[] vertices = new Vertex[Constants.ARRAY_SIZE];
//
//            // Read each line from the file until the end of the file is reached
//            while ((line = reader.readLine()) != null) {
//                // Split the line into two numbers
//                String[] numbers = line.split("\\s+");
//
//                // Check if there are exactly two numbers on the line
//                if (numbers.length == 2) {
//                    try {
//                        double number1 = Double.parseDouble(numbers[0]);
//                        double number2 = Double.parseDouble(numbers[1]);
//                        vertices[vertexCount++] = new Vertex(number1, number2);
//                    } catch (NumberFormatException e) {
//                        System.err.println("Invalid double format on line: " + line);
//                    }
//                }
//            }
//
//            return vertices;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static void main(String[] args) {
//        // Assuming you have a Vertex class with a constructor that takes two doubles
//        Vertex[] vertices = new Vertex[Constants.ARRAY_SIZE]; // Adjust the array size as needed
//        String filePath = "D:\\MapApplication\\src\\main\\resources\\app\\Vertices.txt"; // Replace with the actual file path
//        vertices = readVerticesFromFile(filePath);
//        System.out.println("Vertices read from the file:");
//        System.out.println("Vertices read from the file:");
//        for (int i = 0; i < vertices.length && vertices[i] != null; i++) {
//            System.out.println("Vertex " + (i + 1) + ": x = " + vertices[i].x + ", y = " + vertices[i].y);
//        }
//        // Do something with the vertices array if needed
//    }
//}
