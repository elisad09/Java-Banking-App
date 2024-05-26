package org.poo.cb;
import java.util.*;

public interface Command {
    void execute();
}

class CreateUser implements Command {
    private String command;

    public CreateUser(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String email = info[2];
        String firstName = info[3];
        String lastName = info[4];
        String address = info[5];
        for (int i = 6; i < info.length; i++) {
            address = address.concat(" " + info[i]);
        }

        if (User.userExists(email)) {
            System.out.println("User with " + email + " already exists");
        } else {
            User user = new User.UserBuilder()
                    .withEmail(email)
                    .withfirstName(firstName)
                    .withLastName(lastName)
                    .withAddress(address)
                    .build();
            Bank.users.add(user);
        }
    }
} // done

class AddFriend implements Command {
    private String command;

    public AddFriend(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String userEmail = info[2];
        String friendEmail = info[3];

        if (!User.userExists(userEmail)) {
            System.out.println("User with " + userEmail + " doesn't exist");
            return;
        }

        for (User user : Bank.users) {
            if (user.getEmail().equals(userEmail)) {
                for (User friend : Bank.users) {
                    if (friend.getEmail().equals(friendEmail)) {
                        for (User friendToFind : user.friends) {
                            if (friendToFind.getEmail().equals(friendEmail)) {
                                System.out.println("User with " + friendEmail + " is already a friend");
                            }
                        }
                        user.friends.add(friend);
                        friend.friends.add(user);
                        break;
                    }
                }
            }
        }
    }
} // done

class AddAccount implements Command {
    private String command;

    public AddAccount(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String email = info[2];
        String currency = info[3];

        for (User user : Bank.users) {
            if (user.getEmail().equals(email)) {
                Account account = new Account(currency);
                for(Account acc : user.accounts) {
                    if (acc.currency.equals(currency)) {
                        System.out.println("Account in currency " + currency + " already exists for user");
                        return;
                    }
                }
                user.accounts.add(account);
                break;
            }
        }
    }
} // done

class AddMoney implements Command {
    private String command;

    public AddMoney(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String email = info[2];
        String currency = info[3];
        double amount = Double.parseDouble(info[4]);

        for (User user : Bank.users) {
            if (user.getEmail().equals(email)) {
                for (Account acc : user.accounts) {
                    if (acc.currency.equals(currency)) {
                        acc.increaseAmount(amount);
                    }
                }
            }
        }
    }
} // done

class ExchangeMoney implements Command {
    private String command;

    public ExchangeMoney(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String sourceCurrency = info[3];
        String newCurrency = info[4];
        double amount = Double.parseDouble(info[5]);
        String email = info[2];
        User user = null;
        for (User u : Bank.users) {
            if (u.getEmail().equals(email)) {
                user = u;
            }
        }
        switch (sourceCurrency) {
            case "EUR": {
                Context context = new Context(new ExchangeEUR());
                context.executeStrategy(user, newCurrency, amount);
                break;
            } case "USD": {
                Context context = new Context(new ExchangeUSD());
                context.executeStrategy(user, newCurrency, amount);
                break;
            } case "GBP": {
                Context context = new Context(new ExchangeGBP());
                context.executeStrategy(user, newCurrency, amount);
                break;
            } case "JPY": {
                Context context = new Context(new ExchangeJPY());
                context.executeStrategy(user, newCurrency, amount);
                break;
            } case "CAD": {
                Context context = new Context(new ExchangeCAD());
                context.executeStrategy(user, newCurrency, amount);
                break;
            }
        }

    }
} // done

class TransferMoney implements Command {
    private String command;

    public TransferMoney(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String senderEmail = info[2];
        String receiverEmail = info[3];
        String currency = info[4];
        double amount = Double.parseDouble(info[5]);

        User sender = User.getUserbyEmail(senderEmail);
        User receiver = User.getUserbyEmail(receiverEmail);

        assert sender != null;
        assert receiver != null;

        if (!sender.friends.contains(receiver)) {
            System.out.println("You are not allowed to transfer money to " + receiverEmail);
        }
        for (Account acc : sender.accounts) {
            if (acc.currency.equals(currency)) {
                if (acc.getAmount() < amount) {
                    System.out.println("Insufficient amount in account " + currency + " for transfer");
                    return;
                } else {
                    acc.decreaseAmount(amount);
                    for (Account receiveAcc : receiver.accounts) {
                        if (receiveAcc.currency.equals(currency)) {
                            receiveAcc.increaseAmount(amount);
                        }
                    }
                }
            }
        }
    }
}

class BuyStocks implements Command {
    private String command;

    public BuyStocks(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String email = info[2];
        String company = info[3];
        int noOfStocks = Integer.parseInt(info[4]);

        Stocks stock = Stocks.getStockByCompany(company);
        User user = User.getUserbyEmail(email);
        assert user != null;
        Account account = user.getUserAccountByCurrency("USD");
        assert stock != null;
        assert account != null;
        double stockValue = stock.values[9];

        if (account.getAmount() < stockValue * noOfStocks) {
            System.out.println("Insufficient amount in account for buying stock");
        } else {
            account.decreaseAmount(stockValue * noOfStocks);
            stock.setNoOfStocks(noOfStocks);
            user.stocks.add(stock);
        }
    }
}

class RecommendedStocks implements Command {
    private String command;

    public RecommendedStocks(String command) {
        this.command = command;
    }

    public void execute() {
        List<Stocks> recommendedStocks = new ArrayList<>();
        for (Stocks stock : Bank.stocks) {
            if (stock.recommendedStock()) {
                recommendedStocks.add(stock);
            }
        }

        String stocksOutput = "{\"stockstobuy\":[";

        for (int i = 0; i < recommendedStocks.size() - 1; i++) {
            stocksOutput += "\"" + recommendedStocks.get(i).getCompany() + "\",";
        }
        stocksOutput += "\"" + recommendedStocks.get(recommendedStocks.size() - 1).getCompany() + "\"]}";
        System.out.println(stocksOutput);
    }
}

class ListUser implements Command { // done
    private String command;

    public ListUser(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String email = info[2];

        User user = User.getUserbyEmail(email);

        if (User.userExists(email)) {
            assert user != null;
            user.printUserInfo();
        } else {
            System.out.println("User with " + email + " doesn't exist");
        }
    }
} // done

class ListPortfolio implements Command { // done
    private String command;

    public ListPortfolio(String command) {
        this.command = command;
    }

    public void execute() {
        String[] info = command.split(" ");
        String email = info[2];
        User user = User.getUserbyEmail(email);

        if (User.userExists(email)) {
            assert user != null;
            user.printPortfolio();
        } else {
            System.out.println("User with " + email + " doesn't exist");
        }
    }
} // done

class BuyPremium implements Command {
    private String command;

    public BuyPremium(String command) {
        this.command = command;
    }

    public void execute() {
    }
}

class Invoker {
    static List<Command> commands = new ArrayList<>();

    public void executeCommands() {
        for (Command command : commands) {
            command.execute();
        }
        commands.clear();
    }
}
