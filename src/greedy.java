import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Siddharth on 21-06-2015.
 */
public class greedy {
    private static int noOfCities = 200;

    public static void main(String args[])throws IOException{
        int[][] t = new int[noOfCities][noOfCities];
        int[] r = new int[noOfCities];
        int fitness = 0, cur = 0, next = 0;
        File file = new File("d:/filename.txt");
        int[][] coor=new int[noOfCities][2];
        Scanner scanner = new Scanner(file);
            for (int i = 0; i < noOfCities; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] s = line.split(" ");
                    coor[i][0] = Integer.parseInt(s[0]);
                    coor[i][1] = Integer.parseInt(s[1]);
                }
            }

        for(int i=0;i<noOfCities;i++) {
            for (int j = 0; j < noOfCities; j++) {
                int x=Math.abs(coor[i][0]-coor[j][0]);
                int y=Math.abs(coor[i][1]-coor[j][1]);
                x= (int) Math.pow(x,2);y= (int) Math.pow(y,2);
                x=x+y;
                double d=Math.sqrt(x);
                t[i][j]=x;
            }
            //System.out.println("");
        }

        for (int y = 0; y < noOfCities; y++)
            r[y] = 0;
        for (int x = 1; x < noOfCities; x++) {
            int small = 1000000000;
            r[cur] = 1;
            for (int y = 0; y < noOfCities; y++) {
                if (t[cur][y] < small && t[cur][y] != 0 && r[y] == 0) {
                    small = t[cur][y];
                    next = y;
                }
            }
            fitness += small;
            cur = next;
            System.out.println(next + "  " + fitness);
        }
        //

        System.out.print(fitness);
    }
}
