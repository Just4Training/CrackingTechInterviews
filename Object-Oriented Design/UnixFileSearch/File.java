/*
 * https://leetcode.com/discuss/interview-question/369272/Amazon-or-Onsite-or-Linux-Find-Command
 * 
 * Searching Files based on Name, Extension and Size
 */

public class File {
    String name;
    long size;
    int type;
    String ext;
    boolean isDirectory;
    File[] children;

    File(String name, boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
    }


    class Filter {
        int x = 0;
    }
}
