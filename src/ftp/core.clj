(ns ftp.core
  (:import
   [org.apache.commons.net.ftp FTPClient FTP FTPFile FTPClientConfig]
   [org.apache.commons.net PrintCommandListener]
   [java.io PrintWriter ])
  (:require [clojure.reflect :as r])
  (:use [clojure.pprint :only [print-table]]))

(defn set-timeout
  [client timeout]
  
  "Set Timeouts to FTP client"
  
  (doto client
    (.setDataTimeout timeout)
    (.setConnectTimeout timeout)
    (.setDefaultTimeout timeout)))

(defn create-debug-listener
  []
  (new PrintCommandListener(new PrintWriter java.lang.System/out)))

(defn set-debug-listener
  [client]
  "add debug listener"

  (let [debug-listener (create-debug-listener)]
    (.addProtocolCommandListener client debug-listener)))

(defn create-client-config
  []
  (new FTPClientConfig))

(defn create-client
  [passive? timeout debug?]

  "Create FTP client and set timeout"

  (let [client (new FTPClient)
        config (create-client-config)]
    
    (.configure client config)
    (set-timeout client timeout)

    (if debug?
      (set-debug-listener client))
    
    client))

(defn connect
  [client host timeout]

  "Connect FTP client and set timeout"
  
  (.connect client host)
  (.setSoTimeout client timeout))

(defn logon
  [client user pass]

  (.login client user pass))

(defn close
  [client ]

  "Disconnect FTP client "
  (print "Disconnect FTP client \n")
  
  (.disconnect client))


(defn format-files
  [files]
  (map (fn [f] [(.toFormattedString f)])
               files))

(defn print-files
  [files]
  (clojure.string/join "\n" (format-files files)))

(defn runftp
  [passive? host user pass timeout]

  (print (format "run ftp : %s %s %s %d\n " host user pass timeout))

  (let [client (create-client passive? timeout true)]
    
    ;;(print-table (:members (r/reflect client)))

    (connect client host timeout)
    (logon client user pass)

    client))

(defn -main [& args]
  (let [client (runftp true "192.168.33.62" "vagrant" "vagrant" 60000)]
    (close client)))
