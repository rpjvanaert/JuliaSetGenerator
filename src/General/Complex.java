package General;

public class Complex {
    private float real;
    private float imaginary;

    public Complex(float real, float imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex(){
        this(0,0);
    }

    public float getReal(){
        return this.real;
    }

    public float getImaginary(){
        return this.imaginary;
    }

    public void setReal(float real){
        this.real = real;
    }

    public void setImaginary(float imaginary){
        this.imaginary = imaginary;
    }

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

    public void square(){
        float real = this.real * this.real - this.imaginary * this.imaginary;
        float imaginary = 2 * this.real * this.imaginary;
        this.real = real;
        this.imaginary = imaginary;
    }

    public void addToYourself(Complex otherComplex){
        this.real += otherComplex.real;
        this.imaginary += otherComplex.imaginary;
    }
}
