# UTF-8 以外の文字コード検証

HTML の文字コードに、Shift_JIS、Windows-31J など、UTF-8 以外の文字コードを指定したときの動作を、以下のブラウザについて検証しました。

- Google Chrome 92
- Mozilla Firefox 91

## 前提知識

Shift_JIS では、以下の文字が使用できます。

- JIS X 0201 (いわゆる半角英数字、記号、片仮名など)
- JIS X 0208 (いわゆる全角英数字、記号、平仮名、片仮名、第1水準漢字、第2水準漢字など)

Windows-31J では、上記に加えて以下の文字 (ベンダー外字) が使用できます。

- NEC特殊文字 (丸数字 ①、ローマ数字 Ⅳ、㈱ など)
- IBM拡張文字 (ローマ数字 ⅳ、彅、髙 など)
- NEC選定IBM拡張文字 (IBM拡張文字を別コードで二重登録)

JIS X 0208 の文字を Unicode に変換した場合、Shift_JIS と Windows-31J は本来同じコードにマッピングされるべきですが、現実には異なるコードにマッピングされる文字が存在します (波ダッシュ問題)。

例) 0x8160 (〜) → Shift_JIS で変換: U+301C (波ダッシュ)、Windows-31J で変換: U+FF5E (全角チルダ)

なお、HTML の最新仕様では、文字コードは「UTF-8 でなければならない」と規定されています。

- HTML Living Standard<br>
  <https://html.spec.whatwg.org/multipage/semantics.html#charset>

## HTML の文字コードに Shift_JIS または WIndows-31J を指定したときの動作

```html
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
```

- テキストボックスに、Shift_JIS、Windows-31J でサポートされない文字 (鷗、𠮟 など) も入力できました。
- 文字コードの指定にかかわらず、maxlength は UTF-16 での最大文字数になります。
- フォームで送信したとき、サポートされない文字は数値文字参照 (&amp;#40407;、&amp;#134047; など) で送信されました。
- Shift_JIS と指定した場合も、Windows-31J 相当の動作をするようです。
    - NEC特殊文字、IBM拡張文字などのベンダー外字が送信可能。
    - Shift_JIS、Windows-31J で Unicode のマッピングが異なる文字は、Windows-31J のマッピングで送信。<br>
      例えば、U+FF5E は 0x8160 で送信され、U+301C は文字参照 &amp;#12316; で送信される。

## 以上のことをまとめると

- HTML の文字コード指定にかかわらず、ブラウザ内では Unicode (UTF-16) で処理される。
- HTML の文字コードに UTF-8 以外を指定した場合、余計な変換処理が追加され、文字化けやパフォーマンス劣化の温床となる懸念がある。
- HTML の文字コードに UTF-8 以外を指定した場合、サポートされない文字が数値文字参照に変換されるため、サーバー側で元の文字に戻す処理を追加する必要がある。

HTML の文字コードに UTF-8 以外を指定することは、最新の HTML の仕様に反する上、実用上もデメリットしかありません。
