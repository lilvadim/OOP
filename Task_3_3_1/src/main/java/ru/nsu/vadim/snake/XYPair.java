package ru.nsu.vadim.snake;

public interface XYPair {
    /**
     * If coordinates equal
     *
     * @param a point
     * @param b point
     * @return true if X and Y of points are equal
     */
    static boolean intersect(XYPair a, XYPair b) {
        return a.x() == b.x() && a.y() == b.y();
    }

    /**
     * X coordinate
     */
    int x();

    /**
     * Y coordinate
     */
    int y();

    /**
     * Functional interface for point factory
     *
     * @param <T>
     */
    interface Factory<T extends XYPair> {
        T create(int x, int y);
    }

    /**
     * Helps to do math with points
     *
     * @param <T>
     */
    class Calculator<T extends XYPair> {
        private final Factory<T> factory;

        public Calculator(Factory<T> factory) {
            this.factory = factory;
        }

        public T unit() {
            return factory.create(1, 1);
        }

        public T multiplyCoords(XYPair a, XYPair b) {
            return factory.create(a.x() * b.x(), a.y() * b.y());
        }

        public T add(XYPair a, XYPair b) {
            return factory.create(a.x() + b.x(), a.y() + b.y());
        }
    }
}
