package org.poo.cb;

public interface ExchangeStrategy {
    public void exchangeOperation(User user, String newCurrency, double amount);
}

class ExchangeEUR implements ExchangeStrategy {
    public void exchangeOperation(User user, String newCurrency, double amount) {
        Account eurAccount = null;
        Account newAccount = null;
        for (Account acc : user.accounts) {
            if (acc.currency.equals(newCurrency)) {
                newAccount = acc;
            } else if (acc.currency.equals("EUR")) {
                eurAccount = acc;
            }
        }

        double exchangeRate = 0;
        for (Exchanges ex : Bank.exchanges) {
            if (ex.sourceCurrency.equals(newCurrency)) {
                exchangeRate = ex.converted.get("EUR");
                break;
            }
        }
        assert eurAccount != null;
        assert newAccount != null;

        double sourceAmount = amount * exchangeRate;
        if (sourceAmount > eurAccount.getAmount()) {
            System.out.println("Insufficient amount in account EUR for exchange");
        } else {
            newAccount.increaseAmount(amount);

            if (sourceAmount > eurAccount.getAmount() / 2) {
                eurAccount.decreaseAmount(sourceAmount + (sourceAmount / 100));
            } else {
                eurAccount.decreaseAmount(sourceAmount);
            }
        }
    }
}

class ExchangeUSD implements ExchangeStrategy {
    public void exchangeOperation(User user, String newCurrency, double amount) {
        Account usdAccount = null;
        Account newAccount = null;
        for (Account acc : user.accounts) {
            if (acc.currency.equals(newCurrency)) {
                newAccount = acc;
            } else if (acc.currency.equals("USD")) {
                usdAccount = acc;
            }
        }

        double exchangeRate = 0;
        for (Exchanges ex : Bank.exchanges) {
            if (ex.sourceCurrency.equals(newCurrency)) {
                exchangeRate = ex.converted.get("USD");
                break;
            }
        }
        assert usdAccount != null;
        assert newAccount != null;

        double sourceAmount = amount * exchangeRate;
        if (sourceAmount > usdAccount.getAmount()) {
            System.out.println("Insufficient amount in account USD for exchange");
        } else {
            newAccount.increaseAmount(amount);

            if (sourceAmount > usdAccount.getAmount() / 2) {
                usdAccount.decreaseAmount(sourceAmount + (sourceAmount / 100));
            } else {
                usdAccount.decreaseAmount(sourceAmount);
            }
        }
    }
}

class ExchangeGBP implements ExchangeStrategy {
    public void exchangeOperation(User user, String newCurrency, double amount) {
        Account gbpAccount = null;
        Account newAccount = null;
        for (Account acc : user.accounts) {
            if (acc.currency.equals(newCurrency)) {
                newAccount = acc;
            } else if (acc.currency.equals("GBP")) {
                gbpAccount = acc;
            }
        }

        double exchangeRate = 0;
        for (Exchanges ex : Bank.exchanges) {
            if (ex.sourceCurrency.equals(newCurrency)) {
                exchangeRate = ex.converted.get("GBP");
                break;
            }
        }
        assert gbpAccount != null;
        assert newAccount != null;

        double sourceAmount = amount * exchangeRate;
        if (sourceAmount > gbpAccount.getAmount()) {
            System.out.println("Insufficient amount in account GBP for exchange");
        } else {
            newAccount.increaseAmount(amount);

            if (sourceAmount > gbpAccount.getAmount() / 2) {
                gbpAccount.decreaseAmount(sourceAmount + (sourceAmount / 100));
            } else {
                gbpAccount.decreaseAmount(sourceAmount);
            }
        }
    }
}

class ExchangeJPY implements ExchangeStrategy {
    public void exchangeOperation(User user, String newCurrency, double amount) {
        Account jpyAccount = null;
        Account newAccount = null;
        for (Account acc : user.accounts) {
            if (acc.currency.equals(newCurrency)) {
                newAccount = acc;
            } else if (acc.currency.equals("JPY")) {
                jpyAccount = acc;
            }
        }

        double exchangeRate = 0;
        for (Exchanges ex : Bank.exchanges) {
            if (ex.sourceCurrency.equals(newCurrency)) {
                exchangeRate = ex.converted.get("JPY");
                break;
            }
        }
        assert jpyAccount != null;
        assert newAccount != null;

        double sourceAmount = amount * exchangeRate;
        if (sourceAmount > jpyAccount.getAmount()) {
            System.out.println("Insufficient amount in account JPY for exchange");
        } else {
            newAccount.increaseAmount(amount);

            if (sourceAmount > jpyAccount.getAmount() / 2) {
                jpyAccount.decreaseAmount(sourceAmount + (sourceAmount / 100));
            } else {
                jpyAccount.decreaseAmount(sourceAmount);
            }
        }
    }
}

class ExchangeCAD implements ExchangeStrategy {
    public void exchangeOperation(User user, String newCurrency, double amount) {
        Account cadAccount = null;
        Account newAccount = null;
        for (Account acc : user.accounts) {
            if (acc.currency.equals(newCurrency)) {
                newAccount = acc;
            } else if (acc.currency.equals("CAD")) {
                cadAccount = acc;
            }
        }

        double exchangeRate = 0;
        for (Exchanges ex : Bank.exchanges) {
            if (ex.sourceCurrency.equals(newCurrency)) {
                exchangeRate = ex.converted.get("CAD");
                break;
            }
        }
        assert cadAccount != null;
        assert newAccount != null;

        double sourceAmount = amount * exchangeRate;
        if (sourceAmount > cadAccount.getAmount()) {
            System.out.println("Insufficient amount in account CAD for exchange");
        } else {
            newAccount.increaseAmount(amount);

            if (sourceAmount > cadAccount.getAmount() / 2) {
                cadAccount.decreaseAmount(sourceAmount + (sourceAmount / 100));
            } else {
                cadAccount.decreaseAmount(sourceAmount);
            }
        }
    }
}

class Context {
    private ExchangeStrategy strategy;

    public Context(ExchangeStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(User user, String newCurrency, double amount) {
        strategy.exchangeOperation(user, newCurrency, amount);
    }
}
