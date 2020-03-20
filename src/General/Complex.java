package General;

public class Complex {
    private float real;
    private float imaginary;

    /**
     * Complex Constructor sets real and imaginary values given.
     * @param real
     * @param imaginary
     */

    public Complex(float real, float imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Complex Constructor with no parameters, to 0 + 0i.
     */
    public Complex(){
        this(0,0);
    }

    /**
     * getReal
     * returns float real.
     * @return
     */

    public float getReal(){
        return this.real;
    }

    /**
     * getImaginary
     * returns float imaginary.
     * @return
     */

    public float getImaginary(){
        return this.imaginary;
    }

    /**
     * setReal
     * sets real.
     * @param real
     */

    public void setReal(float real){
        this.real = real;
    }

    /**
     * setImaginary
     * sets imaginary.
     * @param imaginary
     */

    public void setImaginary(float imaginary){
        this.imaginary = imaginary;
    }

    /**
     * square
     * squares itself.
     */

    public void square(){
        float real = this.real * this.real - this.imaginary * this.imaginary;
        float imaginary = 2 * this.real * this.imaginary;
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * addToThis
     * adds complex number to itself
     * @param otherComplex
     */

    public void addToThis(Complex otherComplex){
        this.real += otherComplex.real;
        this.imaginary += otherComplex.imaginary;
    }

    /**
     * toString
     * puts complex number into format ("a + bi").
     * @return
     */

    @Override
    public String toString(){
        if (this.real == 0){
            return this.imaginary + "i";
        } else if (this.imaginary == 0){
            return this.real + "";
        } else if (this.imaginary < 0) {
            return this.real + " - " + (-this.imaginary) + "i";
        } else {
            return this.real + " + " + this.imaginary + "i";
        }
    }
}
