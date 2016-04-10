package sillies;

import gengine.util.coords.Coords2D;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class CoordTest {

//    n  r  0    1    2    3    4    5    6
//
//    0  / 0,0
//    1  | 0,1  1,0
//    2  h 0,2  1,1  2,0
//    3  | 0,3  1,2  2,1  3,0
//    4  \ 0,4  1,3  2,2  3,1  4,0
//    5         1,4  2,3  3,2  4,1  5,0
//    6              2,4  3,3  4,2  5,1  6,0
//    7                   3,4  4,3  5,2  6,1
//    8                        4,4  5,2  6,2
//    9                             5,4  6,3
//   10                                  6,4

    /*
    
    0,0
    1,0  0,1
    2,0  1,1  0,2
    3,0  2,1  1,2
    4,0  3,1  2,2
         4,1  3,2
              4,2
    */
    
    public static void main(String[] args) {
        
//        int h = 5;
//        int w = 3;
//        
//        int[][] map = new int[h][w];
//
//        for (int n = 0; n <= (h-1 + w-1); n++) {
//            System.out.println(n);
//            
//            int rinit = Math.max(n-Math.min(h, w)-1, 0);
//            
//            int rmax  = Math.min(Math.min(h, w)-1, n);
//            
//            for (int r = rinit; r <= rmax; r++) {
//
//                int xoff = Math.max(n-Math.min(h, w), n);
//                
//                int x = xoff - r;
//                int y = r;
//                
//                System.out.print("(" + x + ", " + y + ")\t");
//
//            }
//
//            System.out.println("");
//        }
        
        
        int worldsize = 4;
        
        int worldwidth = 6;
        int worldheight = 2;
        
//        for (int r = 0; r < (2 * worldsize - 1); r++){
//            System.out.println(r);
//            
//            //r=4
//            // 4-r-1 = -1
//              
//            for (int h = Math.max(0, r-worldsize+1); h <= Math.min(r, worldsize-1); h++){
//                Coords2D c = new Coords2D(
//                        (float) r-h,
//                        (float) h);
//                System.out.println(c.toString());
//            }
//            System.out.println();
//        }
        
        for (int r = 0; r < (2 * Math.max(worldheight, worldwidth) - Math.abs(worldheight - worldwidth)) - 1; r++){
            System.out.println(r);
                
            for (int h = Math.max(0, r - worldwidth + 1); h <= Math.min(r, worldheight - 1); h++) {
                Coords2D c = new Coords2D(
                        (float) r - h,
                        (float) h);
                System.out.println(c.toString());
            }
            System.out.println();
        }

    }
}
