%------   IDENTIFICATION   --------------------------------------------------

\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{uds-article}[2002/10/30 v2.0  Standard UdeS article class]
% --- Réalisé à partir du style de thèse de l'UdeS initialement conçu par M. Charles-Antoine Brunet ----

%------   INITIAL CODE   --------------------------------------------------

\def\ExtraBlank{0}

%------   DECLARATION OF OPTIONS   ----------------------------------------

% Utile pour avoir les page impaires a droite quand on fait du 2 pages par pages
\DeclareOption{extraBlank}{\def\ExtraBlank{1}}
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{book}}

%------   EXECUTION OF OPTIONS   ------------------------------------------

\ProcessOptions

%------   PACKAGE LOADING   -----------------------------------------------

\LoadClass[12pt]{book}
\RequirePackage{graphicx,udsbib}

%------   MAIN CODE   -----------------------------------------------------

\setlength{\voffset}{0 in}
\setlength{\topmargin}{-0.75 in}
\setlength{\headheight}{0.35 in}
\setlength{\headsep}{0.4 in}
\setlength{\topskip}{0 in}
\setlength{\textheight}{9 in}
\setlength{\footskip}{0.5 in}
\setlength{\hoffset}{0 in}
\setlength{\oddsidemargin}{0.25 in}
\setlength{\evensidemargin}{-0.25in}
\setlength{\textwidth}{6.5 in}
\setlength{\marginparsep}{0.2 in}
\setlength{\marginparwidth}{0.2 in}
\setlength{\parskip}{1.75ex plus0.25ex minus0.25ex}
\setlength{\parindent}{0 in}

\renewcommand\section{\@startsection{section}{1}{0pt}%
  {-2ex plus-0.5ex minus -0.5ex}{1ex}{\normalfont\Large\sf\bf}}

\renewcommand\subsection{\@startsection{subsection}{2}{0pt}%
  {-2ex plus-0.5ex minus -0.5ex}{0.5ex}{\normalfont\large\sf\bf}}

\renewcommand\subsubsection{\@startsection{subsubsection}{3}{0pt}%
  {-2ex plus-0.5ex minus -0.5ex}{0.5ex}{\normalfont\normalsize\sf\bf}}

\def\partNum{\ifcase\value{part}\or PREMI\`ERE\or DEUXI\`EME\or %
  TROISI\`EME\or QUATRI\`EME\or CINQUI\`EME\or SIXI\`EME\or %
  SEPTI\`EME\or HUITI\`EME\or NEUVI\`EME\or DIXI\`EME\or %
  ONZI\`EME\or DOUZI\`EME\or TREIZI\`EME\or QUATORZI\`EME\or %
  QUINZI\`EME\or SEIZI\`EME\or DIX-SEPTI\`EME\or DIX-HUITI\`EME\or %
  DIX-NEUVI\`EME\or VINGTI\`EME\fi}%

\def\@part[#1]#2{%
  \ifnum \c@secnumdepth >-2\relax
    \refstepcounter{part}%
    \addcontentsline{toc}{part}{\MakeUppercase{\thepart\hspace{1em}#1}}%
  \else
    \addcontentsline{toc}{part}{\MakeUppercase{#1}}%
  \fi
  \markboth{}{}%
  \thispagestyle{empty}
  {
    \centering
    \interlinepenalty \@M
    \normalfont
    \ifnum \c@secnumdepth >-2\relax
      \huge\bfseries \MakeUppercase{\partNum\ \partname}
      \par
      \vskip 20\p@
    \fi
    \Huge \bfseries \MakeUppercase{#2}\par
  }%
  \@endpart
}

\def\@spart#1{%
  {
    \centering
    \interlinepenalty \@M
    \normalfont
    \thispagestyle{empty}
    \Huge \bfseries \MakeUppercase{#1}\par
  }%
  \@endpart
}

\def\@chapter[#1]#2{%
  \ifnum \c@secnumdepth >\m@ne
    \if@mainmatter
      \refstepcounter{chapter}%
      \typeout{\@chapapp\space\thechapter.}%
      \addcontentsline{toc}{chapter}{\protect\numberline{\thechapter}\MakeUppercase{#1}}%
    \else
      \addcontentsline{toc}{chapter}{\MakeUppercase{#1}}%
    \fi
  \else
    \addcontentsline{toc}{chapter}{\MakeUppercase{#1}}%
  \fi
  \chaptermark{\MakeUppercase{#1}}%
  \addtocontents{lof}{\protect\addvspace{10\p@}}%
  \addtocontents{lot}{\protect\addvspace{10\p@}}%
  \if@twocolumn
    \@topnewpage[\@makechapterhead{\MakeUppercase{#2}}]%
  \else
    \@makechapterhead{\MakeUppercase{#2}}%
    \@afterheading
  \fi
}

\def\@schapter#1{
  \if@twocolumn
    \@topnewpage[\@makeschapterhead{\MakeUppercase{#1}}]%
  \else
    \@makeschapterhead{\uppercase{#1}}%
    \@afterheading
  \fi
}

\def\@makechapterhead#1{%
  \vspace*{10\p@}%
  {
    \parindent \z@ \raggedright \normalfont
    \ifnum \c@secnumdepth >\m@ne
      \if@mainmatter
        \LARGE\bfseries\textsf{\@chapapp\ \thechapter}
        \par\nobreak
        \vskip 0\p@
      \fi
    \fi
    \interlinepenalty\@M
    \LARGE \bfseries \textsf{#1} \par\nobreak
    \vskip 30\p@
  }
}
  
\def\@makeschapterhead#1{%
  \vspace*{10\p@}%
  {
    \parindent \z@ \raggedright \normalfont
    \interlinepenalty\@M
    \markboth{#1}{#1}%
    \LARGE\bfseries\textsf{#1} \par\nobreak
    \vskip 30\p@
  }
}

\renewcommand{\appendix}{
  \par
  \setcounter{chapter}{0}%
  \setcounter{section}{0}%
  \gdef\@chapapp{\appendixname}%
  \gdef\thechapter{\@Alph\c@chapter}
  \renewcommand{\baselinestretch}{1}\small\normalsize
}

%-----------------------------------------------------------------------------------

\newcommand{\toNextPage}{\newpage\if@twoside\null\newpage\fi}

\newcommand{\Titre}[1]{\def\letitre{#1}}
\newcommand{\Auteur}[2]{\def\lauteurPrenom{#1} \def\lauteurNom{#2}}
\newcommand{\Auteurs}[1]{\def\lesAuteurs{#1}}
\newcommand{\Date}[1]{\def\ladate{#1}}
\newcommand{\Logo}[1]{\def\lelogo{#1}}

\def\lafaculte{Facult\'e de g\'enie}
%\def\lauteurs
\def\laspecialite{\lenoma}
\def\ledepartement{G\'enie \'electrique et g\'enie informatique}
\def\ladresse{Sherbrooke (Qu\'ebec) Canada} 

\newcommand{\pageTitre}{
\ifnum\ExtraBlank=1\null\thispagestyle{empty}\newpage\fi
\begin{titlepage}
\renewcommand{\baselinestretch}{1}\small\normalsize 
\vspace*{-0.5 in}%
\begin{center}%
\mbox{\includegraphics[draft=false,height=25pt]{udslogo.eps}} \\[0.5ex] %
{\renewcommand{\arraystretch}{1}\begin{tabular}{c}\Large\lafaculte\\\large s\ledepartement\end{tabular}}
\vfill{\LARGE\bf\sf\begin{tabular}{c}\letitre\end{tabular}}\par%
\ifx\empty\lelogo
    \typeout{}\typeout{-----Pas d'image de logo)}\typeout{}%
  \else
    \mbox{\includegraphics[draft=false,height=45pt]{\lelogo}} \\[0.5ex] %
\vfill{\large \begin{tabular}{c}~ \\ \letype \\  \lesAuteurs \end{tabular}}
\vfill{(\ladate)}\vfill%
\vfill{\large \ladresse}%
\end{center}
\end{titlepage}
\newpage\if@twoside\null\thispagestyle{empty}\newpage\fi%
}

\newcommand{\FichierResume}[1]{\def\fichierResume{#1}}
\newcommand{\FichierRemerciements}[1]{\def\fichierMerci{#1}}
\newcommand{\FichiersBibliographie}[2]{\def\fichiersBib{#2}\def\bibStyle{#1}}
\newcommand{\FichierLexique}[1]{\def\fichierLexique{#1}}
\let\FichierGlossaire=\FichierLexique

\newenvironment{memoire}{\begin{leDocument}{M\'emoire de ma\^{\i}trise es sciences appliqu\'ees}}%
                        {\end{leDocument}}
\newenvironment{articleDX}{\begin{leDocument}{}}%
                        {\end{leDocument}}                        
\newenvironment{these}{\begin{leDocument}{Th\`ese de doctorat}}%
                      {\end{leDocument}}

\newenvironment{leDocument}[1]{
  \def\letype{#1}
  \pageTitre
  \bibliographystyle{\bibStyle}
  \renewcommand{\contentsname}{TABLE DES MATI\`ERES}
  \renewcommand{\listfigurename}{LISTE DES FIGURES}
  \renewcommand{\listtablename}{LISTE DES TABLEAUX}
  \renewcommand{\chaptername}{CHAPITRE}
  \renewcommand{\refname}{BIBLIOGRAPHIE}
  \renewcommand{\appendixname}{ANNEXE}
  \renewcommand{\partname}{PARTIE}
  \renewcommand{\figurename}{Figure}
  \renewcommand{\tablename}{Tableau}
  \setcounter{page}{1}
  \pagenumbering{roman}
  \ifx\empty\fichierResume
    \typeout{}\typeout{-----Pas de resume}\typeout{}%
  \else
    \input{\fichierResume}
  \fi%
  \ifx\empty\fichierMerci
    \typeout{}\typeout{-----Pas de remerciements}\typeout{}%
  \else
    \input{\fichierMerci}\toNextPage
  \fi%
  \renewcommand{\baselinestretch}{1}\small\normalsize%
  \tableofcontents
  \listoffigures
  \listoftables\toNextPage
  \ifx\empty\fichierLexique
    \typeout{}\typeout{-----Pas de lexique (glossaire)}\typeout{}%
  \else
    \input{\fichierLexique}\toNextPage
  \fi%
  \setcounter{page}{1}\pagenumbering{arabic}%
}{%
  \toNextPage%
  \renewcommand{\baselinestretch}{1}\small\normalsize
  \ifx\empty\fichiersBib
    \typeout{}\typeout{-----Pas de bibliographie}\typeout{}%
  \else
    \addcontentsline{toc}{chapter}{\MakeUppercase{\refname}}
    \bibliography{\fichiersBib}\toNextPage
  \fi
}

\Titre{AUCUN TITRE\\DE SP\'ECIFI\'E}
\Auteur{Aucunnom}{Desp\'ecifi\'e}
\Date{la date}

\pagestyle{headings}
