(ns ftp.core
  (:import (org.apache.commons.net.ftp FTPClient FTP FTPFile))
  (:require [clojure.reflect :as r])
  (:use [clojure.pprint :only [print-table]]))


(defn foo
  "I don't do a whole lot."
  []
  (println  "Hello, World!"))

(defn setTimeout
  [client timeout]

  (doto client
      (.setDataTimeout timeout)
      (.setConnectTimeout timeout)
      (.setDefaultTimeout timeout)))

(defn createClient
  [timeout]

    (let [client (new FTPClient)]
      (setTimeout client timeout)
      
        client))

(defn runftp
  [host user pass timeout]

  (print (format "run ftp : %s %s %s %d\n " host user pass timeout))

  (let (createClient timeout)]
    
    ;;(print-table (:members (r/reflect client)))
    
    ;; (.connect client host)
    ;; (.setSoTimeout timeout)

    ;; (.logon  user pass)

    client))
  
(defn -main [& args]
  (runftp "192.168.33.62" "vagrant" "vagrant" 1000))
