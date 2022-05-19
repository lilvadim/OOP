package ru.nsu.vadim.snake;

public interface XYPair {
    static boolean intersect(XYPair a, XYPair b) {
        return a.x() == b.x() && a.y() == b.y();
    }

    int x();

    int y();

    interface Factory<T extends XYPair> {
        T create(int x, int y);
    }

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
