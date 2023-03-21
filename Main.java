import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Main {
    static class ToyStore
    {
        private List<Toy> toys;
        private Random random;
        public ToyStore()
        {
            this.toys = new ArrayList<>();
            this.random = new Random();
        }
        public void addToy(int id, String name, int quantity, double frequency)
        {
            Toy toy = new Toy(id, name, quantity, frequency);
            toys.add(toy);
        }
        public void updateToyFrequency(int id, double frequency)
        {
            for (Toy toy : toys)
            {
                if (toy.getId() == id)
                {
                    toy.setFrequency(frequency);
                    break;
                }
            }
        }
        public Toy choosePrizeToy()
        {
            double totalFrequency = 0;
            for (Toy toy : toys)
            {
                totalFrequency += toy.getFrequency();
            }
            double randomValue = random.nextDouble() * totalFrequency;
            double currentFrequency = 0;
            for (Toy toy : toys)
            {
                currentFrequency += toy.getFrequency();
                if (randomValue < currentFrequency)
                {
                    if (toy.getQuantity() > 0)
                    {
                        toy.decreaseQuantity();
                        return toy;
                    }
                    else
                    {
                        System.out.println("Вы ничего не выиграли! Попробуйте снова.");
                        return null;
                    }
                }
            }
            System.out.println("Игрушки недоступны.");
            return null;
        }
        public void writePrizeToyToFile(Toy toy, String filename)
        {
            try
            {
                File file = new File(filename);
                FileWriter writer = new FileWriter(file, true);
                writer.write(toy.getName() + "\n");
                writer.close();
            } catch (IOException e)
            {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
    static class Toy
    {
        private int id;
        private String name;
        private int quantity;
        private double frequency;
        public Toy(int id, String name, int quantity, double frequency)
        {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.frequency = frequency;
        }
        public int getId()
        {
            return id;
        }
        public String getName()
        {
            return name;
        }
        public String toString()
        {
            return "Игрушка [id=" + id + ", name=" + name + ", quantity=" + quantity + ", frequency=" + frequency + "]";
        }
        public int getQuantity()
        {
            return quantity;
        }
        public double getFrequency()
        {
            return frequency;
        }
        public void setFrequency(double frequency)
        {
            this.frequency = frequency;
        }
        public void decreaseQuantity()
        {
            quantity--;
        }
    }
    public static void main(String[] args)
    {
        ToyStore toyStore = new ToyStore();
        toyStore.addToy(1, "Конструктор", 10, 20);
        toyStore.addToy(2, "Пазлы", 15, 30);
        toyStore.addToy(3, "Робот", 8, 15);
        toyStore.addToy(4, "Мягкая игрушка", 12, 35);
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("Меню:");
            System.out.println("1. Разыграть игрушки");
            System.out.println("2. Выход");
            int option = scanner.nextInt();
            if (option == 1)
            {
                Toy prizeToy = toyStore.choosePrizeToy();
                if (prizeToy != null)
                {
                    toyStore.writePrizeToyToFile(prizeToy, "prize_toys.txt");
                    System.out.println("Поздравляем! Вы выиграли " + prizeToy.getName() + "!");
                }
            }
            else if (option == 2)
            {
                break;
            }
            else
            {
                System.out.println("Пожалуйста, выберите пункт меню!.");
            }
        }
    }
}