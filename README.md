# Multiples-ping
# Multi-Ping Network Monitoring Tool

A Java-based network utility for pinging multiple hosts simultaneously with real-time statistics and planned GUI support.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## 📖 Overview

Multi-Ping is a network monitoring tool designed to simplify the process of checking connectivity to multiple hosts. Instead of manually pinging each server or IP address individually, this tool automates the entire process, providing comprehensive statistics and insights into your network's health.

**Perfect for:**
- System administrators monitoring server availability
- Network engineers testing connectivity
- DevOps teams checking infrastructure health
- Anyone needing to ping multiple hosts efficiently

---

## ✨ Features

### Current Features (v1.0)

- 🚀 **Concurrent Ping Execution** - Ping multiple hosts simultaneously for faster results
- 📊 **Detailed Statistics** - Packet loss percentage and average RTT for each host
- 📁 **File-Based Input** - Load IP addresses from text files (one per line)
- 🔍 **Flexible Target Support** - Works with IP addresses and hostnames
- 🖥️ **Cross-Platform** - Supports Linux, macOS, and Windows
- 📈 **Summary Reports** - Get overview statistics across all targets
- ⚡ **Fast & Efficient** - Thread-pool based execution with configurable concurrency
- 💬 **Clear Output Format** - Easy-to-read results at a glance

### 🎨 Planned Features (v2.0 - GUI)

- 🖼️ **Graphical User Interface** - Modern, intuitive UI for easy interaction
- 📝 **Interactive Host Management** - Add/remove/edit hosts directly in the GUI
- 📊 **Real-Time Visualization** - Live charts showing ping statistics
- 🔔 **Alert System** - Desktop notifications for unreachable hosts
- 💾 **History Tracking** - Save and review past ping sessions
- 🎯 **Target Groups** - Organize hosts into logical groups (servers, routers, etc.)
- 🌗 **Dark/Light Theme** - Comfortable viewing in any environment
- 📤 **Export Results** - Save reports as CSV, JSON, or PDF
- ⏰ **Scheduled Monitoring** - Automatic periodic pings with logging
- 📍 **Network Topology View** - Visual representation of monitored hosts

---

## 🛠️ Technical Requirements

### Prerequisites

- **Java Development Kit (JDK)**: Version 17 or higher
- **Maven**: Version 3.8 or higher
- **Operating System**: Linux, macOS, or Windows
- **Network Access**: Ability to send ICMP packets (may require admin/root on some systems)

### System Permissions

On Linux/macOS, ping commands typically require elevated privileges. You may need to:
- Run with `sudo` (not recommended for production)
- Configure capabilities: `sudo setcap cap_net_raw+ep $(which ping)`
- Use the tool as-is (Java executes system ping which handles permissions)

---

## 📁 Project Structure

```
network-admin-tp1/
├── src/
│   ├── main/
│   │   ├── java/com/network/tp1/
│   │   │   ├── Main.java              # Application entry point
│   │   │   ├── PingExecutor.java      # Executes ping commands
│   │   │   ├── PingResult.java        # Data model for results
│   │   │   ├── PingParser.java        # Parses ping output
│   │   │   └── IPFileReader.java      # Reads IP files
│   │   └── resources/
│   └── test/
│       └── java/
├── ips.txt                             # Sample IP address file
├── pom.xml                             # Maven configuration
└── README.md                           # This file
```

---

## 🚀 Installation

### Method 1: Clone and Build

```bash
# Clone the repository
git clone https://github.com/yourusername/multiping-tool.git
cd multiping-tool

# Build with Maven
mvn clean package

# Run the application
java -jar target/tp1-multiping-1.0-SNAPSHOT.jar
```

### Method 2: Import into IDE

**IntelliJ IDEA:**
1. File → Open → Select project folder
2. Maven will auto-import dependencies
3. Run `Main.java`

**Eclipse:**
1. File → Import → Existing Maven Projects
2. Select project folder
3. Right-click `Main.java` → Run As → Java Application

**VS Code:**
1. Open folder in VS Code
2. Install "Extension Pack for Java"
3. Maven will auto-configure
4. Run from Run & Debug panel

---

## 💻 Usage

### Basic Usage

1. **Create an IP list file** (or use the provided `ips.txt`):
```text
# My servers
192.168.1.1
8.8.8.8
google.com
github.com
```

2. **Run the tool**:
```bash
java -jar target/tp1-multiping-1.0-SNAPSHOT.jar ips.txt
```

3. **View results**:
```
=== TP1 Multi-Ping Tool ===

Loaded 4 IP addresses from: ips.txt
Starting ping operations...

Pinging 192.168.1.1...
Pinging 8.8.8.8...
Pinging google.com...
Pinging github.com...

=== Results ===
192.168.1.1 => 100%, 0.817 ms
8.8.8.8 => 100%, 14.223 ms
google.com => 100%, 12.456 ms
github.com => 100%, 23.789 ms

=== Summary ===
Total hosts:      4
Reachable:        4
Unreachable:      0
Errors:           0
Average RTT:      12.821 ms
```

### Command-Line Options

```bash
# Use default ips.txt file
java -jar multiping.jar

# Use custom IP file
java -jar multiping.jar /path/to/custom-ips.txt

# Run specific class (development)
mvn exec:java -Dexec.mainClass="com.network.tp1.Main"
```

### IP File Format

The IP list file supports:
- **IPv4 addresses**: `192.168.1.1`
- **Hostnames**: `google.com`, `server.example.com`
- **Comments**: Lines starting with `#`
- **Empty lines**: For readability

Example:
```text
# Production Servers
192.168.1.10    # Web Server
192.168.1.11    # Database Server

# External Services
8.8.8.8         # Google DNS
1.1.1.1         # Cloudflare DNS

# Development
localhost
```

---

## 🔧 Configuration

Edit these constants in `Main.java` to customize behavior:

```java
// Maximum number of concurrent ping operations
private static final int MAX_CONCURRENT_PINGS = 10;

// Default IP file if none specified
private static final String DEFAULT_IP_FILE = "ips.txt";
```

Edit `PingExecutor.java` for ping parameters:

```java
// Number of packets to send per ping
private static final int PING_COUNT = 4;

// Timeout in seconds
private static final int TIMEOUT_SECONDS = 10;
```



## 🏗️ Implementation Details

### Architecture

The application follows a modular design with clear separation of concerns:

1. **Main.java** - Orchestrates the application flow
2. **PingExecutor.java** - Handles system command execution
3. **PingParser.java** - Extracts statistics from ping output
4. **PingResult.java** - Data transfer object for results
5. **IPFileReader.java** - File I/O operations

### Key Design Decisions

#### ✅ Using System Ping Command
We use the native OS `ping` command via `Runtime.exec()` rather than implementing raw ICMP sockets because:
- **Simplicity**: No need for JNI or external libraries
- **Cross-platform**: Works on Linux, Mac, and Windows
- **Permissions**: System ping handles privileges automatically
- **Reliability**: Mature, well-tested implementation
- **TP Specification**: The assignment explicitly allows this approach

#### ✅ Concurrent Execution
Uses Java's `ExecutorService` with a fixed thread pool:
- Pings multiple hosts simultaneously
- Configurable concurrency limit (default: 10)
- Non-blocking, efficient resource usage

#### ✅ Cross-Platform Parsing
Supports both Linux/Mac and Windows ping output formats:
- **Linux/Mac**: `rtt min/avg/max/mdev = ...`
- **Windows**: `Average = ...ms`

### Libraries Used

- **Java Standard Library**: For all core functionality
- **No external dependencies**: Pure Java implementation
- **JUnit 5** (optional): For unit testing

This approach keeps the project lightweight and educational, focusing on understanding network operations rather than library complexity.

---

## ⚠️ Known Limitations

1. **System Dependency**: Requires `ping` command to be available on the system
2. **Parsing Fragility**: Ping output format varies across OS versions
3. **No Raw ICMP**: Doesn't construct packets from scratch (see TP2 for that)
4. **IPv6 Support**: Currently only handles IPv4 addresses
5. **Firewall Issues**: Some networks block ICMP packets

---

## 🚀 Roadmap

### Phase 1: Core CLI (✅ Complete)
- [x] Basic ping execution
- [x] File-based input
- [x] Statistics calculation
- [x] Concurrent execution
- [x] Cross-platform support

### Phase 2: GUI Development (🔄 In Progress)
- [ ] JavaFX-based graphical interface
- [ ] Interactive host list management
- [ ] Real-time ping visualization
- [ ] Charts and graphs
- [ ] Alert notifications

### Phase 3: Advanced Features (📋 Planned)
- [ ] Continuous monitoring mode
- [ ] Data export (CSV, JSON, PDF)
- [ ] Historical data tracking
- [ ] Network topology visualization
- [ ] Integration with TP3 (NIDS) and TP4 (DNS)

### Phase 4: Enterprise Features (💭 Future)
- [ ] REST API for remote access
- [ ] Web dashboard
- [ ] Email/SMS alerts
- [ ] Database integration
- [ ] Multi-user support

---

## 🤝 Contributing

Contributions are welcome! This is an educational project, but improvements are always appreciated.

### How to Contribute

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request



## 🙏 Acknowledgments

- **Course**: Administration Réseau - Master IDL (GLIA)
- **Assignment**: TP1 - Multiples Ping
- **Institution**: Université Blaise Pascal Clermont-Ferrand
- Inspired by network administration best practices and tools like `fping` and `nmap`

---


**⭐ Star this repository if you find it helpful!**
