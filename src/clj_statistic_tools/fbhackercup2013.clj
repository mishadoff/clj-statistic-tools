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

;; Works approximately 8 hours
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; detect language for all files in directory
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def PATTERN [[:c "# ?include ?[<\"]"]
              [:c "#define"]
              [:c "#import <stdio"]
              [:c-sharp "static void Main ?\\("]
              [:c-sharp "static bool"]
              [:c-sharp "using System\\.IO"]
              [:c-sharp "System\\.Linq"]
              [:f-sharp "open System"]
              [:java "static void main ?\\("]
              [:java "e\\.printStackTrace"]
              [:java "System\\.out\\.print"]
              [:scala "scala\\.io"]
              [:scala "io\\.Source\\."]
              [:scala "\\.zipWithIndex"]
              [:scala "def main ?\\(args ?\\: ?Array\\[String\\]\\)"]
              [:scala "java\\.io\\.\\{"]
              [:clojure "\\(defn "]
              [:go "\"bufio\""]
              [:go "\"fmt\""]
              [:go "\"io/ioutil\""]
              [:perl "#!/usr/bin/perl"]
              [:perl "#!/bin/perl"]
              [:perl "#!perl"]
              [:perl "chomp\\("]
              [:perl "\\$hash\\{"]
              [:perl "\\@chars"]
              [:perl "use warnings"]
              [:ruby "#!/usr/bin/env ruby"]
              [:ruby "\\{ ?\\|"]
              [:ruby "\\.gsub"]
              [:ruby "\\| ?\\w+ ?\\|"]
              [:ruby "[\\ \\.]puts "]
              [:ocaml "Array\\.fold_lefti"]
              [:ocaml "String\\.iter"]
              [:ocaml "Scanf\\.bscanf"]
              [:ocaml "Printf\\.printf"]
              [:pascal "[Ww]riteln"]
              [:pascal "Qsort\\("]
              [:pascal "uses Windows"]
              [:pascal "uses SysUtils"]
              [:pascal "Sender\\: TObject"]
              [:js "getElementById"]
              [:js "console\\.log"]
              [:js "readFileSync\\("]
              [:js "process\\.stdin"]
              [:scheme "\\(define "]
              [:haskell "putStrLn"]
              [:haskell "Control\\.Aplicative"]
              [:haskell "Control\\.Monad"]
              [:haskell "main \\:\\: IO"]
              [:haskell "import System\\.IO"]
              [:haskell "import Data\\.List"]
              [:common-lisp "\\(defun "]
              [:visual-basic "End Function"]
              [:visual-basic "End Select"]
              [:visula-vasic "End Sub"]
              [:php "<\\?php"]
              [:php "<\\?"]
              [:php "strtolower"]
              [:python "__main__"]
              [:python "__author__"]
              [:python "#!/usr/bin/python"]
              [:python "import re"]
              [:python "import heapq"]
              [:python "import itertools"]
              [:python "import sys"]
              [:python "import string"]
              [:python "import fileinput"]
              [:python "in x?range"]
              [:python "with open\\("]
              [:python "writelines"]
              [:python "def main\\(\\)\\:"]
              [:python "in enumerate ?\\("]
              [:python "readlines\\("]
              [:python "sorted\\("]
              [:python "raw_input\\("]
              [:python "from collections"]
              [:python "#!/usr/bin/env python"]
              [:dylan "exit-application\\(0\\)"]
              [:lua "string\\.gmatch"]
              [:bash "#!/usr/bin/bash"]
              [:cocoa "NSLog\\("]
              [:matlab "num2str"]
              [:matlab "fgetl"]
              [:dart "dart\\:io"]

              ;; crap patterns
              [:crap "AssemblyInfo"]
              [:crap "_MACOSX"]
              [:crap "\\.org\\.eclipse\\.jdt"]
              [:crap "\\.classPK"]
              [:crap "�"]
              ])

(def SKIP_CRAP
  #{"1359617412011_671725963.txt" "1359607291698_10994309.txt"
    "1359592577699_1962787219.txt" "1359619355047_1886424395.txt"
    "1359619277627_318473541.txt" "1359613590503_1054982856.txt"
    "1359610917754_488363477.txt" "1359612282194_179780087.txt"
    "1359604230730_2139957593.txt" "1359609379995_1603888362.txt"
    "1359602618225_556918707.txt" "1359618348430_2082188462.txt"
    "1359596777444_954266054.txt" "1359594050557_93355302.txt"
    "1359617933837_460602472.txt" "1359604622148_74024471.txt"
    "1359619140564_938295297.txt" "1359598975828_1550610430.txt"
    "1359607829670_721177332.txt" "1359610475658_96220583.txt"
    "1359613359622_2021448084.txt" "1359605117422_2055812378.txt"
    "1359619033664_161978242.txt"
    "1359610107531_936721251.txt"
    "1359611874099_1378592358.txt"
    "1359604359938_307383404.txt"
    "1359617915102_184220237.txt"
    "1359598994639_380651055.txt"
    "1359606818387_364201503.txt"
    "1359613939784_1987156761.txt"
    "1359612376831_1443928157.txt"
    "1359614750190_660696713.txt"
    "1359600796234_111106567.txt"
    "1359607736491_1236486404.txt"
    "1359612747068_1558790562.txt"
    "1359610882483_663358788.txt"
    "1359603975028_996218067.txt"
    "1359604280929_855669975.txt"
    "1359611611611_220444015.txt"
    "1359598539467_848136396.txt"
    "1359616856690_178950505.txt"
    "1359611870623_183195590.txt"
    "1359603011949_1411650112.txt"
    "1359619337626_1993324210.txt"
    "1359612279925_1566658243.txt"
    "1359598824350_1800107930.txt"
    "1359613029982_1039919041.txt"
    "1359607131530_2045454210.txt"
    "1359613271718_1484534609.txt"
    "1359607803127_1979022837.txt"
    "1359612798636_153132054.txt"
    "1359609219751_693587862.txt"
    "1359613895891_2048707185.txt"
    })

(def DUMB_MATCH
  {"1359619164500_13415300.txt" :actionscript "1359604006917_1299870883.txt" :python
   "1359590890272_1987867577.txt" :python "1359598754653_1518361259.txt" :kotlin
   "1359613288632_1134313080.txt" :groovy "1359609400388_283344025.txt" :groovy
   "1359617923386_118147041.txt" :ruby
   "1359601875008_1726231757.txt" :python
   "1359608677234_692219576.txt" :js
   "1359608761949_314394464.txt" :ruby
   "1359604667801_1270378198.txt" :c-sharp
   "1359611874099_1378592358.txt" :java
   "1359617955970_1738375687.txt" :perl
   "1359602017330_1928024342.txt" :java
   "1359602538906_1091746979.txt" :perl
   "1359603783975_1292302775.txt" :java
   "1359611675400_392098575.txt" :ruby
   "1359591780935_2084806625.txt" :c
   "1359608577791_1390823796.txt" :php
   "1359617348130_1333566.txt" :powershell
   "1359606131553_1176402333.txt" :java
   "1359591180186_1918951643.txt" :java
   "1359618123607_380569260.txt" :python
   "1359612441273_1343609010.txt" :c
   "1359592364034_699499975.txt" :c
   "1359616956648_165521242.txt" :python
   "1359592445579_221272421.txt" :c
   "1359595740223_1234053979.txt" :awk
   "1359618142259_591906533.txt" :perl
   "1359606836436_800945464.txt" :haskell
   "1359611894401_784966666.txt" :python
   "1359609828208_1770788375.txt" :python
   "1359600910599_310435779.txt" :python
   "1359614117888_2017908205.txt" :java
   "1359606068801_396716542.txt" :python
   })

(defn get-file-names [dir]
  (map #(.getName %) (file-seq (clojure.java.io/file dir))))

(defn match? [query source]
  (not (nil? (re-seq (re-pattern query) source))))

(defn detect-lang [file pattern]
  (let [source (slurp file)]
    (loop [[p & ps] pattern]
      (if p
        (if (match? (second p) source) (first p)
            (recur ps))
            :not))))

(defn process-dir-lang-detect [dir pattern]
  (loop [[f & fs] (filter #(.endsWith % ".txt") (get-file-names dir)) mp {} not-detected #{} cnt 1]
    (if f
      (cond (contains? SKIP_CRAP f) (recur fs (assoc mp :crap (inc (get mp :crap 0))) not-detected (inc cnt))
            (contains? DUMB_MATCH f) (recur fs (assoc mp (get DUMB_MATCH f) (inc (get mp (get DUMB_MATCH f) 0))) not-detected (inc cnt))
            :else (let [detected (detect-lang (str dir f) pattern)]
                    (if (= :not detected) (recur fs (assoc mp :not (inc (get mp :not 0))) (conj not-detected f) (inc cnt))
                        (recur fs (assoc mp detected (inc (get mp detected 0))) not-detected (inc cnt)))))
      {:detected mp :not-detected not-detected})
    ))