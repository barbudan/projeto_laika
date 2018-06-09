from .models import Animal,Cao
from .serializer import AnimalSerializer,CaoSerializer,UserAnimalSerializer,UserCaoSerializer
from django.contrib.auth.models import User
from rest_framework import permissions
from .permissions import IsOwnerOrReadOnly

from rest_framework import generics


class AnimaisLista (generics.ListCreateAPIView):
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,IsOwnerOrReadOnly,)
    queryset = Animal.objects.all()
    serializer_class = AnimalSerializer

    def perform_create(self, serializer):
        serializer.save(criador=self.request.user)


class AnimalDetalhe (generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,IsOwnerOrReadOnly,)
    queryset = Animal.objects.all()
    serializer_class = AnimalSerializer


class CaesLista (generics.ListCreateAPIView):
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,IsOwnerOrReadOnly,)
    queryset = Cao.objects.all()
    serializer_class = CaoSerializer

    def perform_create(self, serializer):
        serializer.save(criador=self.request.user)


class CaoDetalhe (generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,IsOwnerOrReadOnly,)
    queryset = Cao.objects.all()
    serializer_class = CaoSerializer


class UserAnimaisLista (generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserAnimalSerializer


class UserAnimalDetalhe(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserAnimalSerializer


class UserCaesLista (generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserCaoSerializer


class UserCaoDetalhe(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserCaoSerializer



