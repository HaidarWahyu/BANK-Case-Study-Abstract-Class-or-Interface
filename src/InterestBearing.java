public interface InterestBearing {
    double MAX_RATE = 0.10;

    double getInterestRate();

    void addInterest();

    default String interestSummary() {
        return "Bunga " + (getInterestRate() * 100) + "% per periode";
    }
}
