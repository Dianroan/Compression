public class Block {
    private int position;
    private int numMatches;
    private char uniqueChar;

    public Block(int position, int numMatches, char uniqueChar) {
        this.position = position;
        this.numMatches = numMatches;
        this.uniqueChar = uniqueChar;
    }

    public Block() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNumMatches() {
        return numMatches;
    }

    public void setNumMatches(int numMatches) {
        this.numMatches = numMatches;
    }

    public char getUniqueChar() {
        return uniqueChar;
    }

    public void setUniqueChar(char uniqueChar) {
        this.uniqueChar = uniqueChar;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((this.position << 3) + this.numMatches);
        bytes[1] = (byte) this.uniqueChar;
        return bytes;
    }
}
