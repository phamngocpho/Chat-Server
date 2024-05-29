create table Users(
    UserID int identity(1,1) primary key,
    FullName nvarchar(100),
    Gender smallint,
    Dob date,
    Gmail nvarchar(max),
    Verified bit,
    PhoneNumber varchar(12),
    [Password] nvarchar(max),
    Auth_code nvarchar(max),
    CreateAt datetime default getdate(),
);


create table User_Relationship(
    RelationshipID int primary key identity(1,1),
    UserID1 int,
    UserID2 int,
    RelationshipType nvarchar(50),
    CreatedAt datetime default GETDATE(),
    foreign key (UserID1) references Users(UserID),
    foreign key (UserID2) references Users(UserID)
)

create table Conversations(
    ConversationsID int identity(1,1) primary key,
    CreatorID int,
    Tittle varchar(50),
    IsGroup bit,
    CreateAt datetime default getdate(),
)

create table Conversations_Member(
    ParticipantsID int identity(1,1) primary key,
    ConversationsID int,
    UserID int,
    foreign key (ConversationsID) references Conversations(ConversationsID),
    foreign key (UserID) references Users(UserID)
)

create table Messages(
    MessageID int identity(1,1) primary key,
    ConversationsID int,
    SenderID int,
    Attachment_file varbinary(max),
    [Message] nvarchar(4000),
    MessageType nvarchar(50),
    CreateAt datetime default getdate(),
    foreign key (ConversationsID) references Conversations(ConversationsID),
    foreign key (SenderID) references Users(UserID)
)

create table Access(
    AccessID int identity(1,1) primary key,
    UserID int,
    OTP_code nchar(6),
    Token nvarchar(max),
    CreateAt datetime default getdate(),
    DeviceID varchar(max),
    foreign key (UserID) references Users(UserID),
)

-- INSERT into Users (FullName, Gender, Dob, Gmail, Verified, PhoneNumber, Password, Auth_code) VALUES (?, ?, ?, ?, 0, ?, CONVERT(VARBINARY(MAX), ?), ?);
--
-- SELECT FORMAT(CreateAt, 'HH:mm') AS OnlyTime
-- FROM Messages;
-- drop table Users