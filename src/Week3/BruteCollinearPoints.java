package Week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {

    private List<LineSegment> results;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        Point[] copyPoints = points.clone();

        if (copyPoints == null)  {
            throw new NullPointerException();
        }


        for (int i = 0 ; i < copyPoints.length ; i++) {
            if (copyPoints[i] == null) {
                throw new NullPointerException();
            }
        }

        results = new ArrayList<LineSegment>();
        solve(sort(copyPoints));

        segments = new LineSegment[results.size()];
        for (int i = 0 ; i < segments.length ; i++) {
            segments[i] = results.get(i);
        }
    }

    private Point[] sort(Point[] points) {
        Arrays.sort(points);
        // detect duplicate
        Point previousPoint = points[0];
        for (int i = 1 ; i < points.length ; i++) {
            if (previousPoint.compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            } else {
                previousPoint = points[i];
            }
        }
        return points;
    }

    private void solve(Point[] sortedPoints) {

        for (int i = 0; i < sortedPoints.length - 3; i++) {
            for (int j = i + 1; j < sortedPoints.length - 2; j++) {
                for (int k = j + 1; k < sortedPoints.length - 1; k++) {
                    for (int l = k + 1; l < sortedPoints.length; l++) {
                        Point[] input = {sortedPoints[i], sortedPoints[j], sortedPoints[k], sortedPoints[l]};
                        LineSegment result = checkCollinear(input);
                        if (result != null) {
                            results.add(result);
                        }
                    }
                }
            }

        }
    }

    private LineSegment checkCollinear(Point[] points) {
        assert points.length == 4;
        double k1 = points[0].slopeTo(points[1]);
        double k2 = points[0].slopeTo(points[2]);
        double k3 = points[0].slopeTo(points[3]);
        if ((k1 == k2) && (k2 == k3)) {
            return new LineSegment(points[0], points[3]);
        }
        return null;
    }


    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        LineSegment[] segments = collinear.segments();
        System.out.println(segments);
        segments[0] = new LineSegment(new Point(0, 0), new Point(1, 1));
        System.out.println(collinear.segments());

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
