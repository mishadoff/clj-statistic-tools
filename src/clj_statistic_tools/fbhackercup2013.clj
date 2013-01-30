(ns fbhackercup2013
  (:require [clj-http.client :as http])
  (:require [clj-webdriver.taxi :as web])
  (:require [clj-webdriver.core :as c])
  (:require [clojure.contrib.math :as math]))

(def CODE_URL_SERVER "https://fbcdn-dragon-a.akamaihd.net/")
;; TODO Directory must exist
(def FILES_DIRECTORY "data/fbhackercup2013/round0/")

(defn generate-filename [url]
  "Filename: timestamp_|hash|.txt"
  (str FILES_DIRECTORY (System/currentTimeMillis) "_" (math/abs (hash url)) ".txt"))

(defn process-url [url]
  (let [source-code (:body (http/get url))
        file-name (generate-filename url)]
    (println "Writing file: " file-name)
    (spit file-name source-code)))

(defn process-page [n]
  (let [url (str "https://www.facebook.com/hackercup/scoreboard?round=185564241586420&page=" n)]
    (web/to url)
    (doseq [e (web/find-elements {:css "a"}) ;; TODO work with just first. remove
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
    (doseq [page-num (range 1 (inc 114))] ;; TODO hardcoded numer of pages
      (println "Processing page: " page-num)
      (process-page page-num))
))