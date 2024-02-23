public class Testing {
    public static void main(String[] args) {
        byte inputByte = 0x2A; // Replace with your byte value

        // Convert the upper nibble to a string representation
        String upperNibbleString = Integer.toBinaryString((inputByte >> 4) & 0xF);

        // Convert the lower nibble to a string representation
        String lowerNibbleString = Integer.toBinaryString(inputByte & 0xF);

        // Concatenate the two nibble strings
        String nibbleString = upperNibbleString + lowerNibbleString;

        System.out.println("Byte: " + inputByte);
        System.out.println("Nibble String: " + nibbleString);
    }
}
