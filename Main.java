package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Running Main");
        } else {
            String exchangeFile = "./src/main/resources/";
            exchangeFile = exchangeFile.concat(args[0]);
            String stocksFile = "./src/main/resources/";
            stocksFile = stocksFile.concat(args[1]);
            String commandsFile = "./src/main/resources/";
            commandsFile = commandsFile.concat(args[2]);

            readCommands(commandsFile);
            createCurrenciesList();
            readExchangeList(exchangeFile);
            readStocksList(stocksFile);

            Invoker invoker = new Invoker();
            invoker.executeCommands();
        }
    }

    public static void readCommands(String commandsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(commandsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("CREATE USER")) {
                    Invoker.commands.add(new CreateUser(line));
                } else if (line.contains("ADD FRIEND")) {
                    Invoker.commands.add(new AddFriend(line));
                } else if (line.contains("ADD ACCOUNT")) {
                    Invoker.commands.add(new AddAccount(line));
                } else if (line.contains("ADD MONEY")) {
                    Invoker.commands.add(new AddMoney(line));
                } else if (line.contains("EXCHANGE MONEY")) {
                    Invoker.commands.add(new ExchangeMoney(line));
                } else if (line.contains("TRANSFER MONEY")) {
                    Invoker.commands.add(new TransferMoney(line));
                } else if (line.contains("BUY STOCKS")) {
                    Invoker.commands.add(new BuyStocks(line));
                } else if (line.contains("RECOMMEND STOCKS")) {
                    Invoker.commands.add(new RecommendedStocks(line));
                } else if (line.contains("LIST USER")){
                    Invoker.commands.add(new ListUser(line));
                } else if (line.contains("LIST PORTFOLIO")) {
                    Invoker.commands.add(new ListPortfolio(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cleanUp();
    }

    public static void createCurrenciesList() {
        Bank.currencies.add("USD");
        Bank.currencies.add("EUR");
        Bank.currencies.add("GBP");
        Bank.currencies.add("JPY");
        Bank.currencies.add("CAD");
    }

    public static void readExchangeList(String exchangeFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(exchangeFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Exchanges exchange = new Exchanges(data[0]);
                exchange.converted.put("EUR", Double.parseDouble(data[1]));
                exchange.converted.put("GBP", Double.parseDouble(data[2]));
                exchange.converted.put("JPY", Double.parseDouble(data[3]));
                exchange.converted.put("CAD", Double.parseDouble(data[4]));
                exchange.converted.put("USD", Double.parseDouble(data[5]));
                Bank.exchanges.add(exchange);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readStocksList(String stocksFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(stocksFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Stocks stock = new Stocks(data[0]);
                for (int i = 1; i < data.length; i++) {
                    stock.values[i - 1] = Double.parseDouble(data[i]);
                }
                Bank.stocks.add(stock);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanUp() {
        Bank.users.clear();
        Bank.exchanges.clear();
        Bank.stocks.clear();
        Bank.currencies.clear();
    }

}