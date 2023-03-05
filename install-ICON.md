# King-ICON-Contract


## OpenJDK 11
```bash
sudo apt install openjdk-11-jdk
```
## Golang
```bash
sudo wget -c https://dl.google.com/go/go1.14.2.linux-amd64.tar.gz -O - | sudo tar -xz -C /usr/local
export PATH=$PATH:/usr/local/go/bin
```
## Python3

**pip3**
```
sudo apt install python3-pip
```

**python3 packages**
```
pip3 install virtualenv setuptools wheel
```

## rocksdb
**Clone RocksDB project**
```
git clone https://github.com/facebook/rocksdb.git
```
**Install RocksDB**
```
cd rocksdb
sudo DEBUG_LEVEL=0 make shared_lib install-shared
```

```
export LD_LIBRARY_PATH=/usr/local/lib
```

## Local ICON chain


**Clone goloop repository**
```bash
git clone https://github.com/icon-project/goloop.git
```
### 1. Build goloop cli
```
cd goloop
make goloop
```

**Check if goloop cli was installed successfully**
```
$ goloop
Goloop CLI

Usage:
  goloop [command]

Available Commands:
  chain       Manage chains
  debug       DEBUG API
  gn          Genesis transaction manipulation
  gs          Genesis storage manipulation
  help        Help about any command
  ks          Keystore manipulation
  rpc         JSON-RPC API
  server      Server management
  stats       Display a live streams of chains metric-statistics
  system      System info
  user        User management
  version     Print goloop version

Flags:
  -h, --help   help for goloop

Use "goloop [command] --help" for more information about a command.
```

### 2. Make docker image

```bash
cd goloop
make gochain-icon-image
```

**Check if docker image was installed successfully**
```bash
$ docker images goloop/gochain-icon
REPOSITORY            TAG       IMAGE ID       CREATED          SIZE
goloop/gochain-icon   latest    73927da2b1a0   20 seconds ago   512MB
```

### 3. Start gochain_local

**Clone gochain_local project**
```
git clone https://github.com/icon-project/gochain-local.git
```

**Start it**
```
cd gochain-local
./run_gochain_icon.sh start
```

**Check if it was started successfully**
```
goloop rpc --uri http://127.0.0.1:9082/api/v3 lastblock
{
  "block_hash": "41b2a3bef4001a33273d55460c967712640b9098cd2bc18754f8dfd7c460f764",
  "version": "2.0",
  "height": 173,
  "time_stamp": 1637308091345452,
  "peer_id": "hxb6b5791be0b5ef67063b3c10b840fb81514db2fd",
  "prev_block_hash": "85a7846e796d9ebe744212e419629a47c6fddd631406e2938d67de4387e03257",
  "merkle_tree_root_hash": "",
  "signature": "",
  "confirmed_transaction_list": []
}
```
