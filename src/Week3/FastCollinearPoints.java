package Week3;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * public class FastCollinearPoints {
 public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
 public           int numberOfSegments()        // the number of line segments
 public LineSegment[] segments()                // the line segments
 }
 *
 */
public class FastCollinearPoints {

    private List<LineSegment> results;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        Point[] copyPoints = points.clone();
        if (copyPoints == null) {
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
        for (int i = 0 ; i < sortedPoints.length - 3 ; i++) {
            Point current = sortedPoints[i];
            Point[] copyPoints = new Point[sortedPoints.length];
            for (int j = 0 ; j < sortedPoints.length ; j++) {

                copyPoints[j] = sortedPoints[j];

            }

            // Bring all the colinear points together && MERGE SORT is STABLE
            Arrays.sort(copyPoints, current.slopeOrder());

            // Take out the results
            int streakCount = 1;
            int startIndex = 0;
            for (int j = 1 ; j < copyPoints.length ; j++) {
                if (copyPoints[j].slopeTo(current) == copyPoints[j - 1].slopeTo(current)) {
                    streakCount ++;

                    if (j == copyPoints.length - 1) {
                        if (streakCount >= 3) {
                            // We garantees there is no duplicates
                            if (current.compareTo(copyPoints[startIndex]) < 0) {
                                results.add(new LineSegment(current, copyPoints[j]));
                            }
                        }
                    }
                } else {
                    if (streakCount >= 3) {
                        // We garantees there is no duplicates
                        if (current.compareTo(copyPoints[startIndex]) < 0) {
                            results.add(new LineSegment(current, copyPoints[j - 1]));
                        }
                    }

                    // resetting
                    streakCount = 1;
                    startIndex = j;
                }
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
