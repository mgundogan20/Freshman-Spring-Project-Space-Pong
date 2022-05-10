package game;

public class Collision {
	
	public static boolean collides(GameObject frs, GameObject sec) {
		if(frs.getShape().equals("circle") && sec.getShape().equals("circle")) {
			return collidesCircle(frs, sec);
		}
		else
			return collidesRect(frs, sec);
	}
	
	public static boolean collidesRect(GameObject frs, GameObject sec) {
		if(rectCollisionTest(frs, sec))
			return true;
		else if(rectCollisionTest(sec, frs))
			return true;
		else
			return false;
	}
	
	public static boolean collidesCircle(GameObject frs, GameObject sec) {
		Vector2D dist = Vector2D.distOfCenters(frs, sec);
		if(dist.getMag() < frs.getRadius()+sec.getRadius())
			return true;
		else
			return false;
	}
	
	private static boolean rectCollisionTest(GameObject frs, GameObject sec) {
		Vector2D topLeft = frs.getPosition();
		Vector2D botRight = Vector2D.sum(frs.getPosition(), new Vector2D(frs.getWidth(), frs.getHeight()));
		Vector2D target;
		for(int i=0; i<2 ;i++) {
			for(int j=0; j<2 ;j++) {
				target = Vector2D.sum(sec.getPosition(), new Vector2D(j*sec.getWidth(), i*sec.getHeight()));
				if(inBounds(topLeft, botRight, target)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean bulletCollision(Vector2D topLeft, int width, int height, int bulletRadius, Vector2D bulletCenterPosition, Vector2D bulletVelocity, double deltaT) {
		Vector2D topRight = Vector2D.sum(topLeft, new Vector2D(width, 0));
		Vector2D bulletPath = Vector2D.sum(bulletCenterPosition, bulletVelocity.scaled(deltaT));
		if(Vector2D.sub(topLeft, bulletPath).getMag()<bulletRadius && topLeft.getY() > bulletPath.getY()) {
			return true;
		}
		else if(Vector2D.sub(topRight, bulletPath).getMag()<bulletRadius && topLeft.getY() > bulletPath.getY()) {
			return true;
		}
		else if(bulletPath.getX()>topLeft.getX() && bulletPath.getX()<topRight.getX()) {
			if(bulletPath.getY()<topLeft.getY()-bulletRadius && bulletPath.getY() > topLeft.getY()-height-bulletRadius)
				return true;
		}
		return false;
	}
	
	public static boolean lineSegmentCircleIntersection(Vector2D start, Vector2D end, double radius1, Vector2D center, double radius2) {
		Vector2D start2Center = Vector2D.sub(center, start);
		Vector2D line = Vector2D.sub(end, start);
		start2Center.projectOn(line);
		if(Vector2D.sub(center, Vector2D.sum(start, start2Center)).getMag() <= radius1+radius2)
			return true;
		else 
			return false;
	}
	
	public static boolean lineSegmentInterSection(Vector2D p1, Vector2D p2, Vector2D p3, Vector2D p4) {
		//p1-p2 and p3-p4 represent line segments
		Vector2D A = Vector2D.sub(p2, p1);
		Vector2D B = Vector2D.sub(p3, p4);
		Vector2D C = Vector2D.sub(p1, p3);
		double denominator = (A.getY()*B.getX())-(A.getX()*B.getY());
		double numerator1 = (B.getY()*C.getX())-(B.getX()*C.getY());
		double numerator2 = (A.getX()*C.getY())-(A.getY()*C.getX());
		if(denominator > 0) {
			if(numerator1 < 0 || numerator1 > denominator) {
				return false;
			}
			if(numerator2 < 0 || numerator2 > denominator) {
				return false;
			}
		}
		else if(numerator1 > 0 || numerator1 < denominator) {
			return false;
		}
		else if(numerator2 > 0 || numerator2 < denominator) {
			return false;
		}
		return true;
	}
	
	private static boolean inBounds(Vector2D topLeft, Vector2D botRight, Vector2D target) {
		if((target.getX()-topLeft.getX())*(target.getX()-botRight.getX()) <= 0
			&& (target.getY()-topLeft.getY())*(target.getY()-botRight.getY()) <= 0)
			return true;
		else
			return false;
	}
}
