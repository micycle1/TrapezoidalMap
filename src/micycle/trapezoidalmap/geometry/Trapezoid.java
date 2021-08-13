package micycle.trapezoidalmap.geometry;

import micycle.trapezoidalmap.tree.Leaf;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Represents a trapezoid object in the trapezoidal map or search structure.
 * <p>
 * Each trapezoid ∆ is determined by: A bottom segment bottom(∆). A top segment
 * top(∆). A left vertex leftp(∆). A right vertex rightp(∆)
 * 
 * @author Tyler Chenhall
 */
public final class Trapezoid {

	// Neighbors of this trapezoid
	// Two trapezoids are neighbors if they share a vertical edge
	private Trapezoid uleft_neighbor;
	private Trapezoid lleft_neighbor;
	private Trapezoid uright_neighbor;
	private Trapezoid lright_neighbor;
	private Leaf owner;

	// variables describing the trapezoid shape
	private PVector leftP;
	private PVector rightP;
	private Segment topSeg;
	private Segment botSeg;
	private PShape poly;

	/**
	 * Constructs a trapezoid object based on the x boundaries and bounding
	 * segments. Sets the neighbor trapezoids to null currently
	 *
	 * @param left   Left x boundary
	 * @param right  Right x boundary
	 * @param top    Segment determining the upper boundary
	 * @param bottom Segment determining the lower boundary
	 */
	public Trapezoid(PVector left, PVector right, Segment top, Segment bottom) {
		leftP = left;
		rightP = right;
		topSeg = top;
		botSeg = bottom;

		uleft_neighbor = null;
		lleft_neighbor = null;
		uright_neighbor = null;
		lright_neighbor = null;
		owner = null;
	}

	/**
	 * Get the left bounding point
	 * 
	 * @return The left vertex
	 */
	public PVector getLeftBound() {
		return leftP;
	}

	/**
	 * Get the right bounding point
	 * 
	 * @return The right bounding vertex
	 */
	public PVector getRightBound() {
		return rightP;
	}

	/**
	 * Get the lower bounding segment
	 * 
	 * @return The lower segment
	 */
	public Segment getLowerBound() {
		return botSeg;
	}

	/**
	 * Get the upper bounding segment for the trapezoid
	 * 
	 * @return The upper segment
	 */
	public Segment getUpperBound() {
		return topSeg;
	}

	/**
	 * Get the trapezoid which lies to the left of this trapezoid below the left
	 * boundary vertex
	 * 
	 * @return The lower left neighbor
	 */
	public Trapezoid getLowerLeftNeighbor() {
		return lleft_neighbor;
	}

	/**
	 * Get the trapezoid which lies to the left of this one, above the left boundary
	 * vertex
	 * 
	 * @return the upper left neighbor trapezoid
	 */
	public Trapezoid getUpperLeftNeighbor() {
		return uleft_neighbor;
	}

	public Trapezoid getLowerRightNeighbor() {
		return lright_neighbor;
	}

	public Trapezoid getUpperRightNeighbor() {
		return uright_neighbor;
	}

	public void setLowerLeftNeighbor(Trapezoid t) {
		lleft_neighbor = t;
	}

	public void setUpperLeftNeighbor(Trapezoid t) {
		uleft_neighbor = t;
	}

	public void setLowerRightNeighbor(Trapezoid t) {
		lright_neighbor = t;
	}

	public void setUpperRightNeighbor(Trapezoid t) {
		uright_neighbor = t;
	}

	/**
	 * Set the leaf which contains this trapezoid
	 * 
	 * @param l The leaf containing this trapezoid
	 */
	public void setLeaf(Leaf l) {
		owner = l;
	}

	/**
	 * Get the leaf containing this trapezoid
	 * 
	 * @return The leaf pointing to this trapezoid
	 */
	public Leaf getLeaf() {
		return owner;
	}

	/**
	 * Return the boundary polygon for this trapezoid
	 * 
	 * @return The boundary Polygon
	 */
	public PShape getBoundaryPolygon() {
		if (poly == null) {
			poly = getPrivateBoundaryPolygon(leftP, rightP, topSeg, botSeg);
		}
		return poly;
	}

	/**
	 * Returns the boundary of the trapezoid as a Polygon object for easy display.
	 *
	 * @return The polygon object representing the boundary of the Trapezoid
	 */
	private PShape getPrivateBoundaryPolygon(PVector left, PVector right, Segment top, Segment bottom) {
		final PVector tl = top.intersect(left.x);
		final PVector tr = top.intersect(right.x);
		final PVector bl = bottom.intersect(left.x);
		final PVector br = bottom.intersect(right.x);

		final PShape polygon = new PShape();
		polygon.setFamily(PShape.PATH);
		polygon.setFill(true);
		polygon.setFill(-255);
		polygon.beginShape();
		polygon.vertex((int) tl.x, (int) tl.y);
		polygon.vertex((int) tr.x, (int) tr.y);
		polygon.vertex((int) br.x, (int) br.y);
		polygon.vertex((int) bl.x, (int) bl.y);
		polygon.endShape(PConstants.CLOSE);
		return polygon;
	}

	/**
	 * Return true if this trapezoid has zero width
	 * 
	 * @return True if the trapezoid is a sliver with zero width
	 */
	public boolean hasZeroWidth() {
		return leftP.x == rightP.x;
	}

	@Override
	/**
	 * Two trapezoids are equal iff they have the same bounding segments
	 */
	public boolean equals(Object t) {
		if (t == null || !(t instanceof Trapezoid)) {
			return false;
		}
		Trapezoid tt = (Trapezoid) t;
		return (this.topSeg == tt.topSeg) && (this.botSeg == tt.botSeg);
	}
}