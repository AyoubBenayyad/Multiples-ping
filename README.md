# Multi-Ping Network Tool 🌐

A simple Java application that pings multiple hosts (websites or IP addresses) and shows you which ones are reachable and how fast they respond.

![Java](https://img.shields.io/badge/Java-17+-orange.svg)

---

## 🤔 What Does This Do?

Instead of manually pinging servers one by one in your terminal, this tool:
- ✅ Pings multiple hosts at once
- 📊 Shows you packet loss percentage
- ⚡ Displays average response time (RTT)
- 🎨 Provides a nice graphical interface (GUI version)
- 📁 Can read hosts from a text file (CLI version)

**Perfect for checking if your servers, websites, or network devices are online!**

---

## 📦 What You Need

- **Java 17 or higher** installed on your computer
  - Check by running: `java -version`
  - Download from: [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)

That's it! No other dependencies needed.

---

## 📁 Project Files Explained

```
src/main/java/org/example/
├── Main.java           # Simple command-line version (reads from ips.txt)
├── PingAppGUI.java     # Graphical interface with progress bar
├── PingService.java    # Does the actual pinging work
└── Host.java           # Stores information about each host
```

### What Each File Does:

- **Main.java**: Basic version - reads IPs from `ips.txt` file and pings them one by one
- **PingAppGUI.java**: Visual version with a window, buttons, table, and progress bar
- **PingService.java**: The "engine" that actually runs ping commands and reads results
- **Host.java**: Simple data holder for IP address, packet loss, RTT, etc.

---

## 🚀 How to Run

### Option 1: Run the GUI Version (Recommended)

1. **Open your IDE** (IntelliJ IDEA, Eclipse, VS Code)
2. **Open the project folder**
3. **Run `PingAppGUI.java`**
4. A window will appear - enter IPs or domains separated by commas:
   ```
   google.com, 8.8.8.8, github.com
   ```
5. Click **"Start Ping"** and watch the magic happen! 🎉

### Option 2: Run the Command-Line Version

1. **Create a file called `ips.txt`** in your project root:
   ```
   google.com
   8.8.8.8
   github.com
   192.168.1.1
   ```

2. **Run `Main.java`** from your IDE

3. **See results in the console**:
   ```
   Pinging google.com...
   google.com => 100%, 12.456 ms

   Pinging 8.8.8.8...
   8.8.8.8 => 100%, 14.223 ms
   ```

### Option 3: Build and Run from Terminal

```bash
# Compile all files
javac -d bin src/main/java/org/example/*.java

# Run GUI version
java -cp bin org.example.PingAppGUI

# OR run CLI version
java -cp bin org.example.Main
```

---

## 🎯 Understanding the Results

### GUI Version

The table shows 4 columns:

| Column | Meaning | Example |
|--------|---------|---------|
| **IP** | The host you pinged | `google.com` |
| **Packet Received %** | How many packets got through | `100%` (all received) |
| **Avg RTT (ms)** | Average response time | `12.45 ms` (fast!) |
| **Reachable** | Is the host online? | `Yes` or `No` |

- **100% received** = Perfect connection ✅
- **0% received** = Host is down or unreachable ❌
- **RTT = +∞** = No response (unreachable)
- **Low RTT** (< 50ms) = Fast connection ⚡
- **High RTT** (> 200ms) = Slow connection 🐌

### CLI Version Output

```
google.com => 100%, 12.456 ms
```
Means:
- ✅ All 4 packets were received (100%)
- ⚡ Average round-trip time was 12.456 milliseconds

```
192.168.1.99 => 0%, +∞
```
Means:
- ❌ No packets received (0%)
- 🔴 Host is unreachable (infinite RTT)

---

## 🎨 GUI Features

### Progress Bar
- Shows **real-time progress** as hosts are being pinged
- Updates smoothly from 0% to 100%
- Displays current host being tested

### Buttons
- **Start Ping** (Green) - Begin pinging the hosts you entered
- **Clear** (Gray) - Clear the results table and input field

### Color Coding
- 🟢 Green "Yes" = Host is reachable
- 🔴 Red "No" = Host is unreachable

---

## 💡 Example Usage

### Testing Your Home Network
```
192.168.1.1     # Your router
192.168.1.10    # Your computer
192.168.1.20    # Your printer
```

### Testing Internet Connectivity
```
8.8.8.8         # Google DNS
1.1.1.1         # Cloudflare DNS
google.com      # Google website
```

### Testing Your Servers
```
myserver.com
api.myapp.com
database.myapp.com
```

---

## 🔧 How It Works (Simple Explanation)

1. **You enter** an IP address or domain name
2. **The program runs** your computer's built-in `ping` command
3. **It sends 4 packets** to the target host
4. **It waits** for responses
5. **It counts** how many came back
6. **It calculates** the average time it took
7. **It shows you** the results in a nice format

Think of it like throwing 4 balls at a wall and measuring:
- How many bounced back? (Packet received %)
- How long did each take? (RTT)

---

## ⚠️ Common Issues

### "Command not found" or Permission Denied
- **Solution**: Make sure `ping` command works in your terminal first
- On Linux/Mac: Try `ping -c 4 google.com`
- On Windows: Try `ping -n 4 google.com`

### All hosts show as unreachable
- **Solution**: Check your internet connection
- Try pinging `127.0.0.1` (your own computer) first

### GUI window doesn't appear
- **Solution**: Make sure you're running `PingAppGUI.java`, not `Main.java`
- Check that Java GUI libraries are installed (usually included with JDK)

### Progress bar stays at 0%
- **Solution**: The ping might be taking time - wait a few seconds
- Some hosts may be slow to respond or blocking pings

---

## 🎓 Learning Points (From TP Assignment)

This project demonstrates:
- ✅ **Exercice 1**: Single host ping with statistics
- ✅ **Exercice 2**: Packet loss % and average RTT calculation
- ✅ **Exercice 3**: Multiple hosts from file
- ✅ **Bonus**: GUI with progress tracking and real-time updates

---

## 📝 Quick Start Checklist

- [ ] Install Java 17+
- [ ] Open project in IDE
- [ ] Run `PingAppGUI.java`
- [ ] Enter some hosts (e.g., `google.com, 8.8.8.8`)
- [ ] Click "Start Ping"
- [ ] Watch the results appear!

---

## 🤝 Need Help?

- **Java not installed?** → [Download Java](https://www.oracle.com/java/technologies/downloads/)
- **IDE not working?** → Try IntelliJ IDEA Community (free)
- **Still stuck?** → Check that `ping` works in your terminal first

---

**Made for the Network Administration course (Master IDL - GLIA) 🎓**

*Simple, effective, and educational network monitoring!*
