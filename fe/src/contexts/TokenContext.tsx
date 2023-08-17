import { createContext, useState, ReactNode } from "react";

type TokenContextType = {
  accessToken: string;
  refreshToken: string;
  setAccessToken: (accessToken: string) => void;
  setRefreshToken: (refreshToken: string) => void;
  profileImage: string;
  setProfileImage: (profileImage: string) => void;
  handleSetProfileImage: (profileImage: string) => void;
};

type TokenProviderProps = {
  children: ReactNode;
};

export const TokenContext = createContext<TokenContextType | undefined>(
  undefined
);

export function TokenProvider({ children }: TokenProviderProps) {
  const [accessToken, setAccessToken] = useState("");
  const [refreshToken, setRefreshToken] = useState("");

  const [profileImage, setProfileImage] = useState("");

  const handleSetProfileImage = (profileImage: string) => {
    if (profileImage === null) {
      const randomIndex = Math.floor(
        Math.random() * randomProfileImages.length
      );
      const randomImage = randomProfileImages[randomIndex];
      console.log(randomImage);
      setProfileImage(randomImage);
      return;
    }
    setProfileImage(profileImage);
  };

  const value: TokenContextType = {
    accessToken,
    refreshToken,
    setAccessToken,
    setRefreshToken,
    profileImage,
    setProfileImage,
    handleSetProfileImage,
  };

  return (
    <TokenContext.Provider value={value}>{children}</TokenContext.Provider>
  );
}

const randomProfileImages = [
  "https://i.namu.wiki/i/XLKZkh5rX37eIMlLo9NtOKUZX4R5HrUS4aWTJzHvXDLx1f94119oDGmyHBqvTt7zLrPb832244Q3MaURdWjB5Q.webp",
  "https://mblogthumb-phinf.pstatic.net/MjAxOTExMDNfMTIw/MDAxNTcyNzExMzg5NjE4.S3sNMojDGrZ4WdYdGXRV-XMrd5R9jyxts4HLVGcZg1cg.kNrbyXXyEU7EHW5DqsGGr9XufBo-NWfGPIdyQ0mI3kcg.JPEG.z_ye0n/IMG_0206.JPG?type=w800",
  "https://i.namu.wiki/i/zvr4ys2bKSv9w-zCJpepZt_YifzhWRKd0VV72D0NfkHnOQ54dhmOiRBDxmyVXo6nUUqXboJGd1iWegn0-kwN2UJ0zgPzwePs_VEoW7wzlIaKLgiUNS4rkSW0q2ynasWFyS-5Ha7xFnvABX_6Q0j8Ug.webp",
  "https://i.namu.wiki/i/sqNk9pG4mNz9Ni6kZBXZ3AoT67Um_aLA4KBhZXvx6fi95f0P0mIW15O7_VHMIeDY4pryKYQ8Fn5lR7sL3bFn4Q.webp",
];
