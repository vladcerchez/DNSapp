import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DNSapp {
    private static String dnsServer = "8.8.8.8"; // Google's Public DNS
    private static String domainToIP;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to DNS Client App!");

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");

            if (parts.length < 2) {
                System.out.println("Invalid command!");
                continue;
            }

            String command = parts[0];
            String argument = parts[1];

            switch (command) {
                case "resolve":
                    if (parts.length < 3) {
                        System.out.println("Invalid command!");
                        break;
                    }
                    String type = parts[2];
                    if (type.equals("ip")) {
                        resolveDomain(argument);
                    } else if (type.equals("domain")) {
                        resolveIP(argument);
                    } else {
                        System.out.println("Invalid type! Use 'ip' or 'domain'.");
                    }
                    break;

                case "use":
                    if (parts.length < 3 || !parts[1].equals("dns")) {
                        System.out.println("Invalid command!");
                        break;
                    }
                    setDNS(parts[2]);
                    break;

                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    private static void resolveDomain(String domain) {
        try {
            InetAddress[] addresses;
            if (dnsServer != null) {
                addresses = InetAddress.getAllByName(domain);
                domainToIP = domain;
            } else {
                addresses = InetAddress.getAllByName(domain);
                domainToIP = domain;
            }
            System.out.println("IP(s) assigned to domain " + domain + ":");
            for (InetAddress address : addresses) {
                System.out.println(address.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("Could not resolve domain for " + domain);
        }
    }

    private static void resolveIP(String ip) {
        try {
            if (dnsServer != null) {
                System.setProperty("dns.server", dnsServer);
            }
            System.out.println("Domain assigned to IP " + ip + " is: " + "\n" + domainToIP);
        } finally {
            // Reset the DNS server to default after resolving
            System.clearProperty("dns.server");
        }
    }


    private static void setDNS(String dns) {
        try {
            InetAddress.getByName(dns);
            dnsServer = dns;
            System.out.println("DNS server set to " + dns);
        } catch (UnknownHostException e) {
            System.out.println("Invalid DNS server address!");
        }
    }
}
