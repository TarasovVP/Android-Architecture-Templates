# Original Git submodules

The original branch `kmp_with_git_submodules_implementation` (commit
`e8ee3e40c0bf90d7f9780be27855c5077d5403c6`) pulls two directories in as
real Git submodules. In this catalog the contents are **vendored**
(plain files, no nested `.git`) so the code is visible right after a normal
`git clone`, without `git submodule update --init`.

| Path | Original URL | Pinned commit |
|------|--------------|---------------|
| `shared` | https://github.com/TarasovVP/architecture_template_shared.git | `45fbbdb89723ced76b42785b68167fd2ec7f6d7b` |
| `web` | https://github.com/TarasovVP/architecture_template_web.git | `60cede935aee96c69b020fbd082e7f948273ad06` |

The `web` repository itself declares `shared` as a nested submodule (same URL,
pinned to the same `45fbbdb...` commit). That nested copy is vendored at
`web/shared`, and its `.gitmodules` file is not carried over.

## What was removed from the vendored copies

- the `.kotlin/` directory of the `web` repository (generated metadata klibs),
- `.DS_Store` files,
- the `.gitmodules` file of the `web` repository.

Nothing else was changed.

## Real submodule architecture

The genuine submodule setup (a `.gitmodules` file and gitlinks) is preserved
only on the original branch:
https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_git_submodules_implementation

To recreate it there:

```sh
git clone --recurse-submodules -b kmp_with_git_submodules_implementation \
  https://github.com/TarasovVP/Android-Architecture-Templates.git
```
