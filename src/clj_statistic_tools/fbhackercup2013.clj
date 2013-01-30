(ns fbhackercup2013
  (:require [clj-http.client :as http])
  (:require [clj-webdriver.taxi :as web])
  (:require [clj-webdriver.core :as c]))

(def CODE_URL_SERVER "https://fbcdn-dragon-a.akamaihd.net/")
(def FILES_DIRECTORY "data/fbhackercup2013/round0/")

(defn process-url [url]
  (let [source-code (:body (http/get url))
        file-name (str FILES_DIRECTORY (hash url) ".txt")] ;; TODO consider other options as file name
    (println "Writing file [" file-name "]")
    (spit file-name source-code)))

(defn process-page [n]
  (let [url (str "https://www.facebook.com/hackercup/scoreboard?round=185564241586420&page=" n)]
    (web/to url)
    (for [e (web/find-elements {:css "a"}) ;; TODO work with just first. remove
                 :let [url (c/attribute e "href")]
                 :when (and url (.startsWith url CODE_URL_SERVER))]
             (process-url url))))

(defn run [username password]
  (do
    ;; Initialize driver, open starting page
    (web/set-driver! {:browser :firefox} "http://facebook.com")
    ;; Input login details
    (web/input-text "#email" username)
    (web/input-text "#pass" password)
    (web/submit "#pass")
    ;; For each scoreboard page
    (process-page 1)))