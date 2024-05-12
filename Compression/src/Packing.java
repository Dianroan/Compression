import java.util.ArrayList;

public class Packing {
    private String fullSentence;
    private String buffer;
    private String temp;
    private int size;
    private int currentIndex;
    private ArrayList<Byte> byteList;
    private ArrayList<Block> blockList;

    public String getFullSentence() {
        return fullSentence;
    }

    public void setFullSentence(String fullSentence) {
        this.fullSentence = fullSentence;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public ArrayList<Byte> getByteList() {
        return byteList;
    }

    public void setByteList(ArrayList<Byte> byteList) {
        this.byteList = byteList;
    }

    public ArrayList<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<Block> blockList) {
        this.blockList = blockList;
    }

    public Packing() {
        this.fullSentence = "";
        this.byteList = new ArrayList<>();
    }

    public Packing(String fullSentence) {
        this.fullSentence = fullSentence.toLowerCase() + '\u0000';
        this.currentIndex = 0;
        this.buffer = "";
        this.temp = "";
        this.size = this.fullSentence.length();
        this.blockList = new ArrayList<>();
        this.byteList = new ArrayList<>();

        for (; currentIndex < 6; currentIndex++) {
            if (currentIndex == size) {
                break;
            }
            temp += this.fullSentence.charAt(currentIndex);
        }
    }

    public boolean findUniqueChar() {
        char currentChar = temp.charAt(0);
        int repetition = 1;
        int position = 0;

        for (int i = buffer.length() - 1; i >= 0; i--) {
            if (buffer.charAt(i) == currentChar) {
                position = buffer.length() - i;
                for (int x = 1; x < 6; x++) {
                    repetition++;
                    currentChar = temp.charAt(x);
                    if (temp.charAt(x) != buffer.charAt(i + x)) {
                        break;
                    }
                    if (x == 5) {
                        currentChar = fullSentence.charAt(currentIndex);
                        currentIndex++;
                        this.buffer = this.buffer.charAt(7) + this.temp + currentChar;
                        temp = "";
                        for (int z = 0; z < 7; z++) {
                            if (currentIndex == size) {
                                break;
                            }
                            temp += this.fullSentence.charAt(currentIndex);
                            currentIndex++;
                        }
                        Block block = new Block(position, repetition, currentChar);
                        this.blockList.add(block);
                        return true;
                    }
                }
                break;
            }
        }

        this.buffer += this.temp.substring(0, repetition);
        if (this.buffer.length() > 8) {
            this.buffer = this.buffer.substring(repetition);
        }
        this.temp = this.temp.substring(repetition);
        for (int i = 0; i < repetition && currentIndex < size; i++) {
            temp += fullSentence.charAt(currentIndex);
            currentIndex++;
        }
        Block block = new Block(position, repetition - 1, currentChar);
        this.blockList.add(block);
        return currentChar != '\u0000';
    }

    public void toBytes() {
        for (Block block : blockList) {
            byte[] b = block.toBytes();
            for (byte value : b) {
                this.byteList.add(value);
            }
        }
    }

    public ArrayList<Block> toBlock(ArrayList<Byte> bytes) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (int i = 0; i < bytes.size(); i += 2) {
            int repetition = bytes.get(i) & 0b111;
            int position = (bytes.get(i) & 0b1111000) / 8;
            char character = (char) bytes.get(i + 1).byteValue();
            Block block = new Block(position, repetition, character);
            blocks.add(block);
        }
        return blocks;
    }

    public void toStringFormat() {
        int currentIndex = 0;
        for (Block block : blockList) {
            int lastIndex = currentIndex;
            for (int x = 0; x < block.getNumMatches(); x++) {
                fullSentence += fullSentence.charAt(lastIndex - block.getPosition() + x);
                currentIndex++;
            }
            currentIndex++;
            fullSentence += block.getUniqueChar();
        }
    }

    public void stringToBytes(String byteString) {
        String[] preBytes = byteString.split(" ");
        for (String byteValue : preBytes) {
            this.byteList.add(Byte.parseByte(byteValue));
        }
    }
}
